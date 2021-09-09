package com.example.application.profiling;

import org.ehcache.sizeof.SizeOf;
import org.ehcache.sizeof.VisitorListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class SizeOfCalculator {

    private final Object rootRef;
    private final String[] prefixes;
    private final FilteringVisitorListener filteringVisitorListener;

    private final HashMap<String, List<ObjectInstanceSize>> classnameToInstanceSizes = new HashMap<>();

    private SizeOfCalculator(Object rootRef, String... fqClassnamePrefixes) {
        this.rootRef = rootRef;
        this.prefixes = fqClassnamePrefixes;
        this.filteringVisitorListener = new FilteringVisitorListener();
    }

    Logger logger = Logger.getLogger(SizeOfCalculator.class.getName());

    private final SizeOf sizeOf = SizeOf.newInstance(true, true); // filters can be passed here
    //private final SizeOf sizeOf = new AgentSizeOf(new PassThroughFilter(),true, true); // filters can be passed here
    //private final SizeOf sizeOf = new UnsafeSizeOf(new PassThroughFilter(),true, true); // filters can be passed here
    //private final SizeOf sizeOf = new ReflectionSizeOf(new PassThroughFilter(),true, true); // filters can be passed here

    public interface ObjectInstanceSize extends Comparable<ObjectInstanceSize> {
        String getDescription();

        long getDeepSize();
    }

    public interface ClassTotalSize extends Comparable<ClassTotalSize> {
        String getClassName();

        long getTotalSize();

        ObjectInstanceSize[] getObjectInstanceSizes();
    }

    private static class ClsSize implements ClassTotalSize {
        private final String className;
        private final ObjectInstanceSize[] objectInstanceSizes;

        ClsSize(String className, ObjectInstanceSize[] objectInstanceSizes) {
            this.className = className;
            this.objectInstanceSizes = objectInstanceSizes;
        }

        @Override
        public String getClassName() {
            return this.className;
        }

        @Override
        public long getTotalSize() {
            long retVal = 0;
            for (ObjectInstanceSize ois : objectInstanceSizes) {
                retVal += ois.getDeepSize();
            }
            return retVal;
        }

        @Override
        public ObjectInstanceSize[] getObjectInstanceSizes() {
            return objectInstanceSizes;
        }

        @Override
        public int compareTo(ClassTotalSize o) {
            return Long.compare(this.getTotalSize(), o.getTotalSize());
        }
    }

    private static class ObjSize implements ObjectInstanceSize {
        private final String desc;
        private final long dSize;

        ObjSize(String description, long deepSize) {
            this.desc = description;
            this.dSize = deepSize;
        }

        @Override
        public String getDescription() {
            return desc;
        }

        @Override
        public long getDeepSize() {
            return dSize;
        }

        @Override
        public int compareTo(ObjectInstanceSize o) {
            return Long.compare(dSize, o.getDeepSize());
        }
    }


    private class FilteringVisitorListener implements VisitorListener {
        public void visited(Object object, long size) {

            String className = object.getClass().getName();
            for (String prefix : SizeOfCalculator.this.prefixes) {
                if (className.startsWith(prefix)) {
                    ObjectInstanceSize ois = new ObjSize(getDescription(object), sizeOf.deepSizeOf(object));
                    List<ObjectInstanceSize> others = SizeOfCalculator.this.classnameToInstanceSizes.computeIfAbsent(className, k -> new ArrayList<>());
                    others.add(ois);
                    break;
                }
            }
        }
    }

    private static String getDescription(Object o) {
        return o.getClass().getName() + "@" + System.identityHashCode(o);
    }


    public static ClassTotalSize[] calculateDeepSizesOf(Object rootRef, String... fqClassnamePrefixes) {
        return new SizeOfCalculator(rootRef, fqClassnamePrefixes).calc();
    }

    private ClassTotalSize[] calc() {
        sizeOf.deepSizeOf(filteringVisitorListener, this.rootRef);

        ArrayList<ClassTotalSize> classTotalSizes = new ArrayList<>();
        classnameToInstanceSizes.forEach((className, objectInstanceSizes) -> {
            Collections.sort(objectInstanceSizes); // sort
            Collections.reverse(objectInstanceSizes); // sort

            ClassTotalSize cts = new ClsSize(className, objectInstanceSizes.toArray(new ObjectInstanceSize[0]));
            classTotalSizes.add(cts);
        });

        Collections.sort(classTotalSizes);
        Collections.reverse(classTotalSizes);

        return classTotalSizes.toArray(new ClassTotalSize[0]);
    }
}
