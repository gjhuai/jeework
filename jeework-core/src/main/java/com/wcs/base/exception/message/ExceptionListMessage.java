package com.wcs.base.exception.message;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;

/**
 * Exception message that produces a list of exceptions and their stack traces
 * for logging.  This is typically used in lifecycle events which accumulate
 * exceptions across observers.
 * 
 * @author Chris Guan
 *
 */
public class ExceptionListMessage implements ExceptionMessage, Serializable
{

   private static final long serialVersionUID = 3445187707771082346L;

   private List<Throwable> causes;
   private String message;

   public ExceptionListMessage(List<Throwable> throwables)
   {
      this.causes = throwables;
   }

   public String getAsString()
   {
      if (message == null)
      {
         generateMessage();
      }
      return message;
   }

   private void generateMessage()
   {
      StringWriter writer = new StringWriter();
      PrintWriter messageBuffer = new PrintWriter(writer);
      messageBuffer.print("Exception List with ");
      messageBuffer.print(causes.size());
      messageBuffer.print(" exceptions:\n");
      int i = 0;
      for (Throwable throwable : causes)
      {
         messageBuffer.print("Exception ");
         messageBuffer.print(i);
         messageBuffer.print(" :\n");
         throwable.printStackTrace(messageBuffer);
      }
      messageBuffer.flush();
      message = writer.toString();
   }

}
