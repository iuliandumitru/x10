#ifndef X10_LANG_ERROR_H
#define X10_LANG_ERROR_H

#include <x10aux/config.h>
#include <x10aux/RTT.h>

#include <x10/lang/Throwable.h>
#include <x10/lang/Box.h>

namespace x10 {

    namespace lang {

        class Error : public Throwable {
        public:
            RTT_H_DECLS;
            
            static x10aux::ref<Error> _make();
            static x10aux::ref<Error> _make(x10aux::ref<String> message);
            static x10aux::ref<Error> _make(x10aux::ref<Throwable> cause);
            static x10aux::ref<Error> _make(x10aux::ref<String> message,
                                            x10aux::ref<Throwable> cause);

            static const x10aux::serialization_id_t _serialization_id;

            virtual void _serialize_id(x10aux::serialization_buffer &buf, x10aux::addr_map &m) {
                buf.write(_serialization_id,m);
            }

            template<class T> static x10aux::ref<T> _deserializer(x10aux::serialization_buffer &buf);
        };

        template<class T> x10aux::ref<T> Error::_deserializer(x10aux::serialization_buffer &buf){
            x10aux::ref<Error> this_ = new (x10aux::alloc<Error>()) Error();
            this_->_deserialize_body(buf);
            return this_;
        }
    }
}


#endif
// vim:tabstop=4:shiftwidth=4:expandtab
