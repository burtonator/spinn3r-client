#!/usr/bin/python

# Python imports
import sys
import os

# proto buff inports
from protoStream_pb2 import ProtoStreamDelimiter, ProtoStreamHeader
from spinn3rApi_pb2  import Entry
from google.protobuf import message

# our imports
import varint



class ProtoStreamDecoderExcption (Exception): pass

SUPPORTED_VERSION = "1.0"
MAX_VARINT_LENGTH = 10

def ProtoStreamDecoder ( protoClass, expectedType, fileObject ):

    buf = ""

    ( buf, header )  = readDelimited( buf, ProtoStreamHeader, fileObject )
    version = header.version

    if version != SUPPORTED_VERSION:
        message = "Version mismatch expected '%s' got '%s'\n" % ( SUPPORTED_VERSION, version )
        raise ProtoStreamDecoderExcption ( message )

    entryType = header.default_entry_type

    if entryType != expectedType:
        message = "Type mismatch expected '%s' got '%s'\n" % ( expectedType, entryType )
        raise ProtoStreamDecoderExcption ( message )


    while True:
        ( buf, delimiter ) = readDelimited( buf, ProtoStreamDelimiter, fileObject )

        if delimiter.delimiter_type == ProtoStreamDelimiter.END:
            break # We are done

        elif delimiter.delimiter_type == ProtoStreamDelimiter.ENTRY:
            ( buf, entry ) = readDelimited( buf, protoClass, fileObject )

            yield entry

        else:
            message = "Unexpected delimiter type %s\n" % delimiter.delimiter_type
            raise ProtoStreamDecoderExcption ( message )



def readDelimited (  buf, protoClass, fileObject ):

    if len(buf) < MAX_VARINT_LENGTH:
        buf = buf + fileObject.read( MAX_VARINT_LENGTH - len(buf) )

    try:
        (length, pos) = varint.decodeVarint( buf, 0 )

    except varint.NotEnoughDataExcption as error:
        raise ProtoStreamDecoderExcption ( error )

    buf      = buf[pos:]
    readSize = length - len(buf)

    if readSize > 0:
        buf = buf + fileObject.read( readSize )

    result = protoClass ()

    try:
        result.ParseFromString( buf[:length] )

    except message.DecodeError as error:
        raise ProtoStreamDecoderExcption ( error )

    buf = buf[length:]

    return ( buf, result )
    


def main ():
    fileName = sys.argv[1]

    for fileName in sys.argv[1:]:
        fileObject = open( fileName )

        decoder = ProtoStreamDecoder ( Entry, 'contentApi.Entry', fileObject )

        try:
            for entry in decoder:
                message = "entry: %s" % ( entry.source.title )
                print message.encode( "UTF8" )

        except ProtoStreamDecoderExcption as error:
            print "ProtoStreamDecoderExcption: %s" % ( error )

if __name__ == "__main__":
    sys.exit(main())
