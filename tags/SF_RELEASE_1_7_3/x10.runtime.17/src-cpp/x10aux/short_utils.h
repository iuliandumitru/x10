#ifndef X10AUX_SHORT_UTILS_H
#define X10AUX_SHORT_UTILS_H

#include <x10aux/config.h>
#include <x10aux/ref.h>

namespace x10 {
    namespace lang {
        class String;
    }
}


namespace x10aux {
    class short_utils {
    public:
        static const ref<x10::lang::String> toString(x10_short value, x10_int radix);
        static const ref<x10::lang::String> toHexString(x10_short value);
        static const ref<x10::lang::String> toOctalString(x10_short value);
        static const ref<x10::lang::String> toBinaryString(x10_short value);
        static const ref<x10::lang::String> toString(x10_short value);
        static x10_short parseShort(const ref<x10::lang::String>& s, x10_int radix);
        static x10_short parseShort(const ref<x10::lang::String>& s);
        static x10_short reverseBytes(x10_short value);
    };
}

#endif
// vim:tabstop=4:shiftwidth=4:expandtab
