//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.ehcache.sizeof;

import com.example.application.views.helloworldflow.HelloWorldFlowView;
import org.ehcache.sizeof.ObjectGraphWalker.Visitor;
import org.ehcache.sizeof.filters.CombinationSizeOfFilter;
import org.ehcache.sizeof.filters.SizeOfFilter;
import org.ehcache.sizeof.impl.AgentSizeOf;
import org.ehcache.sizeof.impl.ReflectionSizeOf;
import org.ehcache.sizeof.impl.UnsafeSizeOf;
import org.ehcache.sizeof.util.WeakIdentityConcurrentMap;

import java.util.List;

public abstract class SizeOf {
    private final ObjectGraphWalker walker;

    public SizeOf(SizeOfFilter fieldFilter, boolean caching, boolean bypassFlyweight) {
        Object visitor;
        if (caching) {
            visitor = new SizeOf.CachingSizeOfVisitor();
        } else {
            visitor = new SizeOf.SizeOfVisitor();
        }

        this.walker = new ObjectGraphWalker((Visitor)visitor, fieldFilter, bypassFlyweight);
    }

    public abstract long sizeOf(Object var1);

    public long deepSizeOf(VisitorListener listener, Object... obj) {
        return this.walker.walk(listener, obj);
    }

    public long deepSizeOf(Object... obj) {
        return this.walker.walk((VisitorListener)null, obj);
    }

    public static SizeOf newInstance(SizeOfFilter... filters) {
        return newInstance(true, true, filters);
    }

    public static SizeOf newInstance(boolean bypassFlyweight, boolean cache, SizeOfFilter... filters) {
        CombinationSizeOfFilter filter = new CombinationSizeOfFilter(filters);

        try {
            return new AgentSizeOf(filter, cache, bypassFlyweight);
        } catch (UnsupportedOperationException var9) {
            try {
                return new UnsafeSizeOf(filter, cache, bypassFlyweight);
            } catch (UnsupportedOperationException var8) {
                try {
                    return new ReflectionSizeOf(filter, cache, bypassFlyweight);
                } catch (UnsupportedOperationException var7) {
                    throw new UnsupportedOperationException("A suitable SizeOf engine could not be loaded: " + var9 + ", " + var8 + ", " + var7);
                }
            }
        }
    }

    private class CachingSizeOfVisitor implements Visitor {
        private final WeakIdentityConcurrentMap<Class<?>, Long> cache;

        private CachingSizeOfVisitor() {
            this.cache = new WeakIdentityConcurrentMap();
        }

        public long visit(Object object) {
            Class<?> klazz = object.getClass();
            Long cachedSize = (Long)this.cache.get(klazz);
            if (cachedSize == null) {
                if (klazz.isArray()) {
                    return SizeOf.this.sizeOf(object);
                } else {
                    long size = SizeOf.this.sizeOf(object);
                    this.cache.put(klazz, size);
                    return size;
                }
            } else {
                return cachedSize;
            }
        }
    }

    private class SizeOfVisitor implements Visitor {
        private SizeOfVisitor() {
        }

        public long visit(Object object) {
            return SizeOf.this.sizeOf(object);
        }
    }

    public List<HelloWorldFlowView.ObjectWrapper> getChildrenOfDesc(Object o){
        return this.walker.getWrappedChildren(o);
    }
}
