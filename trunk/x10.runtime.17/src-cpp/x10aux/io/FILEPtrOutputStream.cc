#include <x10aux/config.h>
#include <x10aux/ref.h>
#include <x10aux/alloc.h>

#include <x10aux/io/FILEPtrOutputStream.h>

#include <x10/lang/Rail.h>
#include <x10/io/IOException.h>


using namespace x10aux;
using namespace x10aux::io;
using namespace x10::lang;
using namespace x10::io;

FILE* FILEPtrStream::check_stream(FILE* stream) {
#ifndef NO_EXCEPTIONS
/* TODO
    if (stream == NULL) throw ref<IOException>(new (alloc<IOException>()) IOException(x10::lang::String("Null file pointer")));
*/
#endif
    return stream;
}

void FILEPtrOutputStream::_vprintf(const char* format, va_list parms) {
    ::vfprintf(_stream, format, parms);
}

void FILEPtrOutputStream::flush() {
    ::fflush(_stream);
}

void FILEPtrOutputStream::write(const ref<Rail<x10_byte> >& b,
                                x10_int off, x10_int len) {
    ::fwrite(((x10_byte*)b->raw())+off*sizeof(x10_byte), sizeof(x10_byte), len*sizeof(x10_byte), _stream);
}

void FILEPtrOutputStream::write(x10_int b) {
    ::fputc((char)b, _stream);
}

void FILEPtrOutputStream::write(const char* s) {
    ::fprintf(_stream, "%s", s);
}

