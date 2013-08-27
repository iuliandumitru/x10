/*
 *  This file is part of the X10 project (http://x10-lang.org).
 *
 *  This file is licensed to You under the Eclipse Public License (EPL);
 *  You may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *      http://www.opensource.org/licenses/eclipse-1.0.php
 *
 *  (C) Copyright IBM Corporation 2006-2010.
 */

#ifndef X10_IO_NATIVEFILE_H
#define X10_IO_NATIVEFILE_H

#include <x10aux/config.h>
#include <x10aux/serialization.h>

#include <x10/lang/X10Class.h>

#include <x10/lang/String.h>

namespace x10 {

    namespace io {

        class File__NativeFile : public x10::lang::X10Class {
            public:
            RTT_H_DECLS_CLASS;

        private:

            x10::lang::String* path;

        public:

            static File__NativeFile* _make(x10::lang::String* s);
            static File__NativeFile* _make__tm__(x10tm::TMThread *SelfTM, x10::lang::String* s);
            File__NativeFile* _constructor(x10::lang::String* s) {
                path = s;
                return this;
            }

            static const x10aux::serialization_id_t _serialization_id;

            virtual x10aux::serialization_id_t _get_serialization_id() { return _serialization_id; };

            virtual void _serialize_body(x10aux::serialization_buffer &buf);

            static x10::lang::Reference* _deserializer(x10aux::deserialization_buffer &buf);

            virtual void _deserialize_body(x10aux::deserialization_buffer& buf);

            virtual x10::lang::String* getPath() { return path; }

            virtual x10::lang::String* getAbsolutePath();

            virtual x10::lang::String* getCanonicalPath();

            virtual x10_boolean canRead();

            virtual x10_boolean canWrite();

            virtual x10_boolean exists();

            virtual x10_boolean isDirectory();

            virtual x10_boolean isFile();

            virtual x10_boolean isHidden();

            virtual x10_long lastModified();

            virtual x10_boolean setLastModified(x10_long t);

            virtual x10_long length();

            ///

			virtual x10::lang::String* getPath__tm__(x10tm::TMThread *SelfTM) { return path; }

			virtual x10::lang::String* getAbsolutePath__tm__(x10tm::TMThread *SelfTM) {
				return getAbsolutePath();
			}

			virtual x10::lang::String* getCanonicalPath__tm__(x10tm::TMThread *SelfTM) {
				return getCanonicalPath();
			}

			virtual x10_boolean canRead__tm__(x10tm::TMThread *SelfTM) {
				return canRead();
			}

			virtual x10_boolean canWrite__tm__(x10tm::TMThread *SelfTM) {
				return canWrite();
			}

			virtual x10_boolean exists__tm__(x10tm::TMThread *SelfTM) {
				return exists();
			}

			virtual x10_boolean isDirectory__tm__(x10tm::TMThread *SelfTM) {
				return isDirectory();
			}

			virtual x10_boolean isFile__tm__(x10tm::TMThread *SelfTM) {
				return isFile();
			}

			virtual x10_boolean isHidden__tm__(x10tm::TMThread *SelfTM) {
				return isHidden();
			}

			virtual x10_long lastModified__tm__(x10tm::TMThread *SelfTM) {
				return lastModified();
			}

			virtual x10_boolean setLastModified__tm__(x10tm::TMThread *SelfTM, x10_long t) {
				return setLastModified(t);
			}

			virtual x10_long length__tm__(x10tm::TMThread *SelfTM) {
				return length();
			}


        };
    }
}

#endif
// vim:tabstop=4:shiftwidth=4:expandtab
