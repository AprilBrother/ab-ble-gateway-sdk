import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

public void doPost(HttpServletRequest request, HttpServletResponse response)

    throws ServletException, IOException {

        System.out.println("doPost");

        InputStream in = request.getInputStream();

        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(in);

        int maplen = unpacker.unpackMapHeader();

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

        int arrHeader = unpacker.unpackArrayHeader();

        System.out.println("value BinaryHeader:"+ arrHeader);

        for(int i = 0; i < arrHeader; i++) {

            int binaryHeader = unpacker.unpackBinaryHeader();

            byte[] bytes = new byte[binaryHeader];

            unpacker.readPayload(bytes);

            System.out.println("bytes hexString:"+ bytes2HexStr(bytes));

        }

        System.out.println("---------------------------------");

        unpacker.close();

    }


    /*输入16进制byte[]输出16进制字符串*/
    public static String bytes2HexStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


}
