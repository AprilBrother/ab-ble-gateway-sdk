import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

public void doPost(HttpServletRequest request, HttpServletResponse response)

    throws ServletException, IOException {

        System.out.println("doPost");

        InputStream in = request.getInputStream();

        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(in);

        intmaplen = unpacker.unpackMapHeader();

        System.out.println("key:"+ unpacker.unpackString());

        System.out.println("value:"+ unpacker.unpackString());

        System.out.println("key:"+ unpacker.unpackString());

        System.out.println("value:"+ unpacker.unpackInt());

        System.out.println("key:"+ unpacker.unpackString());

        System.out.println("value:"+ unpacker.unpackInt());

        System.out.println("key:"+ unpacker.unpackString());

        System.out.println("value:"+ unpacker.unpackString());

        System.out.println("key:"+ unpacker.unpackString());

        System.out.println("value:"+ unpacker.unpackString());

        System.out.println("key:"+ unpacker.unpackString());

        intarrHeader = unpacker.unpackArrayHeader();

        System.out.println("value BinaryHeader:"+ arrHeader);

        for(inti = 0; i < arrHeader; i++) {

            intbinaryHeader = unpacker.unpackBinaryHeader();

            byte[] bytes = newbyte[binaryHeader];

            unpacker.readPayload(bytes);

            System.out.println("bytes hexString:"+ bytes2HexStr(bytes));

        }

        System.out.println("---------------------------------");

        unpacker.close();

    }



public static String bytes2HexStr(byte[] bytes) {

    char[] hexChars = newchar[bytes.length * 2];

    for(intj = 0; j < bytes.length; j++) {

        intv = bytes[j] & 0xFF;

        hexChars[j * 2] = hexArray[v >>> 4];

        hexChars[j * 2+ 1] = hexArray[v & 0x0F];

    }

    returnnewString(hexChars);

}
