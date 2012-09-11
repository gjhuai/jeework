package com.wcs.base.exception;

import java.util.List;

import com.wcs.base.exception.message.ExceptionKeyMessage;
import com.wcs.base.exception.message.ExceptionListMessage;
import com.wcs.base.exception.message.ExceptionMessage;
import com.wcs.base.exception.message.ExceptionStringMessage;

/**
 * 支持以下形式的构建异常的方式：
 * 1. 字符串构建： TransactionException(String rawMessage) 
 * 2. 异常cause构建： TransactionException(Throwable e)
 * 3. KeyMessage 构建： TransactionException(E key, Object... args)，基于 cal10n 的MessageConveyor构建
 * 4. 异常cause和KeyMessage构建： TransactionException(E key, Exception e, Object... args)
 * 5. 多异常cause构建： TransactionException(List<Throwable> errors)
 * 
 * @author Chris Guan
 */
public class TransactionException extends RuntimeException
{
    private static final long serialVersionUID = 2L;

    private ExceptionMessage message;


    public TransactionException(String rawMessage) {
        this.message = new ExceptionStringMessage(rawMessage);
    }

   /**
    * Creates a new exception with the given cause.
    * 
    */
   public TransactionException(Throwable e)
   {
      super(e);
      this.message = new ExceptionStringMessage(e.getLocalizedMessage());
   }

   /**
    * Creates a new exception with the given localized message key and optional
    * arguments for the message.
    * 
    * @param <E> The enumeration type for the message keys
    * @param key The localized message to use
    * @param args Optional arguments to insert into the message
    */
   public <E extends Enum<?>> TransactionException(E key, Object... args)
   {
      this.message = new ExceptionKeyMessage(key, args);
   }

   /**
    * Creates a new exception with the given localized message key, the cause
    * for this exception and optional arguments for the message.
    * 
    * @param <E> The enumeration type for the message keys
    * @param key The localized message to use
    * @param args Optional arguments to insert into the message
    */
   public <E extends Enum<?>> TransactionException(E key, Exception e, Object... args)
   {
      super(e);
      this.message = new ExceptionKeyMessage(key, args);
   }

   /**
    * Creates a new exception based on a list of throwables.  The throwables are not
    * used as the cause, but the message from each throwable is included as the message
    * for this exception.
    * 
    * @param errors A list of throwables to use in the message
    */
   public TransactionException(List<Throwable> errors)
   {
      super();
      this.message = new ExceptionListMessage(errors);
   }

   @Override
   public String getLocalizedMessage()
   {
      return getMessage();
   }

   @Override
   public String getMessage()
   {
      return message.getAsString();
   }
   
}
