// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Reader {
   private final int BUFFER_SIZE = 65536;
   private DataInputStream din;
   private byte[] buffer;
   private int bufferPointer;
   private int bytesRead;

   public Reader() {
      this.din = new DataInputStream(System.in);
      this.buffer = new byte[65536];
      this.bufferPointer = this.bytesRead = 0;
   }

   public Reader(String file_name) throws IOException {
      this.din = new DataInputStream(new FileInputStream(file_name));
      this.buffer = new byte[65536];
      this.bufferPointer = this.bytesRead = 0;
   }

   public String readLine() throws IOException {
      byte[] buf = new byte[64];
      int cnt = 0;

      byte c;
      while((c = this.read()) != -1) {
         if (c == 10) {
            if (cnt != 0) {
               break;
            }
         } else {
            buf[cnt++] = (byte)c;
         }
      }

      return new String(buf, 0, cnt - 1);
   }

   public String readLine(int bufsize) throws IOException {
      byte[] buf = new byte[bufsize];
      int cnt = 0;

      byte c;
      while((c = this.read()) != -1) {
         if (c == 10) {
            if (cnt != 0) {
               break;
            }
         } else {
            buf[cnt++] = (byte)c;
         }
      }

      return new String(buf, 0, cnt - 1);
   }

   public int nextInt() throws IOException {
      int ret = 0;

      byte c;
      for(c = this.read(); c <= 32; c = this.read()) {
      }

      boolean neg = c == 45;
      if (neg) {
         c = this.read();
      }

      do {
         ret = ret * 10 + c - 48;
      } while((c = this.read()) >= 48 && c <= 57);

      return neg ? -ret : ret;
   }

   public long nextLong() throws IOException {
      long ret = 0L;

      byte c;
      for(c = this.read(); c <= 32; c = this.read()) {
      }

      boolean neg = c == 45;
      if (neg) {
         c = this.read();
      }

      do {
         ret = ret * 10L + (long)c - 48L;
      } while((c = this.read()) >= 48 && c <= 57);

      return neg ? -ret : ret;
   }

   public double nextDouble() throws IOException {
      double ret = 0.0;
      double div = 1.0;

      byte c;
      for(c = this.read(); c <= 32; c = this.read()) {
      }

      boolean neg = c == 45;
      if (neg) {
         c = this.read();
      }

      do {
         ret = ret * 10.0 + (double)c - 48.0;
      } while((c = this.read()) >= 48 && c <= 57);

      if (c == 46) {
         while((c = this.read()) >= 48 && c <= 57) {
            ret += (double)(c - 48) / (div *= 10.0);
         }
      }

      return neg ? -ret : ret;
   }

   private void fillBuffer() throws IOException {
      this.bytesRead = this.din.read(this.buffer, this.bufferPointer = 0, 65536);
      if (this.bytesRead == -1) {
         this.buffer[0] = -1;
      }

   }

   private byte read() throws IOException {
      if (this.bufferPointer == this.bytesRead) {
         this.fillBuffer();
      }

      return this.buffer[this.bufferPointer++];
   }

   public void close() throws IOException {
      if (this.din != null) {
         this.din.close();
      }
   }
}
