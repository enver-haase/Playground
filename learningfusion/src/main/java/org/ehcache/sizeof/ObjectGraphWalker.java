//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.ehcache.sizeof;

import java.lang.ref.SoftReference;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

import com.example.application.views.helloworldflow.HelloWorldFlowView;
import org.ehcache.sizeof.filters.SizeOfFilter;
import org.ehcache.sizeof.util.WeakIdentityConcurrentMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class ObjectGraphWalker {
    private static final Logger LOG = LoggerFactory.getLogger(ObjectGraphWalker.class);
    private static final boolean USE_VERBOSE_DEBUG_LOGGING = true;
    private final WeakIdentityConcurrentMap<Class<?>, SoftReference<Collection<Field>>> fieldCache = new WeakIdentityConcurrentMap();
    private final WeakIdentityConcurrentMap<Class<?>, Boolean> classCache = new WeakIdentityConcurrentMap();
    private final boolean bypassFlyweight;
    private final SizeOfFilter sizeOfFilter;
    private final ObjectGraphWalker.Visitor visitor;

    ObjectGraphWalker(ObjectGraphWalker.Visitor visitor, SizeOfFilter filter, boolean bypassFlyweight) {
        if (visitor == null) {
            throw new NullPointerException("Visitor can't be null");
        } else if (filter == null) {
            throw new NullPointerException("SizeOfFilter can't be null");
        } else {
            this.visitor = visitor;
            this.sizeOfFilter = filter;
            this.bypassFlyweight = bypassFlyweight;
        }
    }

    private final DepTree depTree = new DepTree();

    public List<HelloWorldFlowView.ObjectWrapper> getWrappedChildren(Object o) {
        return depTree.getChildrenListDesc(o);
    }

    private class DepTree {
        private final IdentityHashMap<Object, List<Object>> tree = new IdentityHashMap<>();
        private final HashMap<Object, List<HelloWorldFlowView.ObjectWrapper>> descTree = new HashMap<>();

        public List<HelloWorldFlowView.ObjectWrapper> getChildrenListDesc(Object desc){
            if (!descTree.containsKey(desc)){
                descTree.put(desc, new ArrayList<>());
            }
            return descTree.get(desc);
        }

        private List<Object> getChildrenList(Object o){
            if (!tree.containsKey(o)){
                tree.put(o, new ArrayList<>());
            }
            return tree.get(o);
        }

        public void clear(){
            tree.clear();
            descTree.clear();
        }
    }

    long walk(Object... root) {
        return this.walk((VisitorListener)null, root);
    }

    long walk(VisitorListener visitorListener, Object... root) {
        depTree.clear();

        StringBuilder traversalDebugMessage;
        if (USE_VERBOSE_DEBUG_LOGGING) {
            traversalDebugMessage = new StringBuilder();
        } else {
            traversalDebugMessage = null;
        }

        long result = 0L;
        Deque<Object> toVisit = new ArrayDeque();
        Set<Object> visited = Collections.newSetFromMap(new IdentityHashMap());
        int i;
        if (root != null) {
            if (traversalDebugMessage != null) {
                traversalDebugMessage.append("visiting ");
            }

            Object[] var8 = root;
            int var9 = root.length;

            for(i = 0; i < var9; ++i) {
                Object object = var8[i];
                nullSafeAdd(toVisit, depTree, null, object);
                if (traversalDebugMessage != null && object != null) {
                    traversalDebugMessage.append(object.getClass().getName()).append("@").append(System.identityHashCode(object)).append(", ");
                }
            }

            if (traversalDebugMessage != null) {
                traversalDebugMessage.deleteCharAt(traversalDebugMessage.length() - 2).append("\n");
            }
        }

        while(true) {
            while(true) {
                Object ref;
                do {
                    if (toVisit.isEmpty()) {
                        if (traversalDebugMessage != null) {
                            traversalDebugMessage.append("Total size: ").append(result).append(" bytes\n");
                            LOG.debug(traversalDebugMessage.toString());
                        }

                        return result;
                    }

                    ref = toVisit.pop();
                } while(!visited.add(ref));

                Class<?> refClass = ref.getClass();
                if (!this.byPassIfFlyweight(ref) && this.shouldWalkClass(refClass)) {
                    if (refClass.isArray() && !refClass.getComponentType().isPrimitive()) {
                        for(i = 0; i < Array.getLength(ref); ++i) {
                            nullSafeAdd(toVisit, depTree, ref, Array.get(ref, i));
                        }
                    } else {
                        Iterator var16 = this.getFilteredFields(refClass).iterator();

                        while(var16.hasNext()) {
                            Field field = (Field)var16.next();

                            try {
                                nullSafeAdd(toVisit, depTree, ref, field.get(ref));
                            } catch (IllegalAccessException var13) {
                                throw new RuntimeException(var13);
                            }
                        }
                    }

                    long visitSize = this.visitor.visit(ref);
                    if (visitorListener != null) {
                        visitorListener.visited(ref, visitSize);
                    }

                    if (traversalDebugMessage != null) {
                        traversalDebugMessage.append("  ").append(visitSize).append("b\t\t").append(ref.getClass().getName()).append("@").append(System.identityHashCode(ref)).append("\n");
                    }

                    result += visitSize;
                } else if (traversalDebugMessage != null) {
                    traversalDebugMessage.append("  ignored\t").append(ref.getClass().getName()).append("@").append(System.identityHashCode(ref)).append("\n");
                }
            }
        }
    }

    private Collection<Field> getFilteredFields(Class<?> refClass) {
        SoftReference<Collection<Field>> ref = (SoftReference)this.fieldCache.get(refClass);
        Collection<Field> fieldList = ref != null ? (Collection)ref.get() : null;
        if (fieldList != null) {
            return fieldList;
        } else {
            Collection<Field> result = this.sizeOfFilter.filterFields(refClass, getAllFields(refClass));
            if (USE_VERBOSE_DEBUG_LOGGING && LOG.isDebugEnabled()) {
                Iterator var5 = result.iterator();

                while(var5.hasNext()) {
                    Field field = (Field)var5.next();
                    if (Modifier.isTransient(field.getModifiers())) {
                        LOG.debug("SizeOf engine walking transient field '{}' of class {}", field.getName(), refClass.getName());
                    }
                }
            }

            this.fieldCache.put(refClass, new SoftReference(result));
            return result;
        }
    }

    private boolean shouldWalkClass(Class<?> refClass) {
        Boolean cached = (Boolean)this.classCache.get(refClass);
        if (cached == null) {
            cached = this.sizeOfFilter.filterClass(refClass);
            this.classCache.put(refClass, cached);
        }

        return cached;
    }

    private static void nullSafeAdd(Deque<Object> toVisit, DepTree tree, Object parent, Object o) {
        if (o != null) {
            toVisit.push(o);
            tree.getChildrenList(parent).add(o);
            tree.getChildrenListDesc(parent).add(HelloWorldFlowView.ObjectWrapper.wrap(o));
        }
    }

    private static Collection<Field> getAllFields(Class<?> refClass) {
        Collection<Field> fields = new ArrayList();

        for(Class klazz = refClass; klazz != null; klazz = klazz.getSuperclass()) {
            Field[] var3 = klazz.getDeclaredFields();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Field field = var3[var5];
                if (!Modifier.isStatic(field.getModifiers()) && !field.getType().isPrimitive()) {
                    try {
                        field.setAccessible(true);
                    } catch (SecurityException var8) {
                        LOG.error("Security settings prevent Ehcache from accessing the subgraph beneath '{}' - cache sizes may be underestimated as a result", field, var8);
                        continue;
                    } catch (RuntimeException var9) {
                        LOG.warn("The JVM is preventing Ehcache from accessing the subgraph beneath '{}' - cache sizes may be underestimated as a result", field, var9);
                        continue;
                    }

                    fields.add(field);
                }
            }
        }

        return fields;
    }

    private boolean byPassIfFlyweight(Object obj) {
        if (!this.bypassFlyweight) {
            return false;
        } else {
            FlyweightType type = FlyweightType.getFlyweightType(obj.getClass());
            return type != null && type.isShared(obj);
        }
    }

    interface Visitor {
        long visit(Object var1);
    }
}
