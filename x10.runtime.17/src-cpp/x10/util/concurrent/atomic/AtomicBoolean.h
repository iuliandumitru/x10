/*
 * (c) Copyright IBM Corporation 2008
 *
 * This file is part of XRX/C++ native layer implementation.
 */

#ifndef X10_UTIL_CONCURRENT_ATOMIC_ATOMICBOOLEAN_H
#define X10_UTIL_CONCURRENT_ATOMIC_ATOMICBOOLEAN_H

#include <x10rt17.h>
#include <x10/lang/Ref.h>

namespace x10 {
    namespace util {
        namespace concurrent {
            namespace atomic {

                /**
                 * Native implementation of AtomicBoolean.
                 */
                class AtomicBoolean : public x10::lang::Ref {
                public:
                    RTT_H_DECLS;

                    static x10aux::ref<AtomicBoolean> _make();
                    static x10aux::ref<AtomicBoolean> _make(x10_boolean val);

                protected:
                    x10aux::ref<AtomicBoolean> _constructor(x10_boolean val) { _val = (val ? 1 :0); return this; }

                private:
                    /*
                     * Am x10_int that is constrained to a 0/1 and interpret as an x10_ boolean.
                     * We do this so that we know that compareAndSet_32 can work on the whole memory word.
                     */
                    volatile x10_int _val;

                public:
                    x10_boolean get() { return _val == 1; }
                    
                    void set(x10_boolean newVal) { _val = (newVal ? 1 : 0); }

                    x10_boolean compareAndSet(x10_boolean expect, x10_boolean update) {
                        x10_int expectI = expect ? 1 : 0;
                        x10_int updateI = update ? 1 : 0;
                        x10_int oldVal = x10aux::atomic_ops::compareAndSet_32(&_val, expectI, updateI) == expectI;
                        return oldVal == 1;
                    }
                    
                    x10_boolean weakCompareAndSet(x10_boolean expect, x10_boolean update) {
                        // TODO: for minor optimization on ppc we could add a weakCompareAndSet in atomic_ops and use that here
                        x10_int expectI = expect ? 1 : 0;
                        x10_int updateI = update ? 1 : 0;
                        x10_int oldVal = x10aux::atomic_ops::compareAndSet_32(&_val, expectI, updateI) == expectI;
                        return oldVal == 1;
                    }

                    x10_boolean getAndSet(x10_boolean update) {
                        x10_boolean oldVal = get();
                        set(update);
                        return oldVal;
                    }
                        
                    x10aux::ref<x10::lang::String> toString() {
                        return x10aux::boolean_utils::toString(_val);
                    }
                };
            }
        }
    }
}
        
#endif /* X10_UTIL_CONCURRENT_ATOMIC_ATOMICBOOLEAN_H */

// vim:tabstop=4:shiftwidth=4:expandtab
