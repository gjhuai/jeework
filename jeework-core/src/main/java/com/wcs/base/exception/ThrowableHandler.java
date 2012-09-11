package com.wcs.base.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wcs.base.trace.domain.StoredLog;
import com.wcs.base.trace.service.StoredLogService;
import com.wcs.base.trace.service.StoredLogService.LogLevel;
import com.wcs.base.util.DateUtils;
import com.wcs.base.util.StringUtils;

/**
 *
 *
 */
public class ThrowableHandler {


        private static final transient Logger handleLogger = LoggerFactory.getLogger(ThrowableHandler.class);


        /**
         *
         * Handle Throwable with handleLogger.
         *
         * @param throwable
         */
        public static void handle(Throwable throwable) {
                handle(throwable, handleLogger);
        }


        /**
         *
         * Handle Throwable with handleLogger.
         *
         * @param throwable
         * @param logger
         *
         */
        public static void handle(Throwable throwable, Logger logger) {
                throwable.printStackTrace();
                logger.error(throwable.getMessage(), throwable);
        }


        /**
         *
         * Handle Throwable with handleLogger with user message.
         *
         * @param userMessage
         * @param throwable
         *
         *
         */
        public static void handle(String userMessage, Throwable throwable) {
                handle(userMessage, throwable, handleLogger);
        }


        /**
         *
         * Handle Throwable with userLogger with user message.
         *
         * @param userMessage
         * @param throwable
         * @param logger
         *
         */
        public static void handle(String userMessage, Throwable throwable, Logger logger) {
                throwable.printStackTrace();
                logger.error(userMessage, throwable.getMessage(), throwable);
        }


        /**
         *
         * Handle Throwable and then throw again. with handleLogger
         *
         * @param userMessage
         * @param throwable
         *
         */
        public static void handleThrow(String userMessage, Throwable throwable) {
                handleThrow(userMessage, throwable, handleLogger);
        }


        /**
         *
         * @param throwable
         * @param logger
         */
        public static void handleThrow(Throwable throwable, Logger logger) {
                handleThrow(null, throwable, logger);
        }


        /**
         *
         * Handle Throwable and then throw again. with userLogger
         *
         * @param userMessage
         * @param throwable
         * @param logger
         */
        public static void handleThrow(String userMessage, Throwable throwable, Logger logger) {
                throwable.printStackTrace();


                if (StringUtils.hasText(userMessage))
                        logger.error(userMessage, throwable.getMessage(), throwable);
                else
                        logger.error(throwable.getMessage(), throwable);


                throw new TransactionException(throwable);
        }


        /**
         *
         * @param throwable
         * @param logger
         *
         */
        public static void handleStoreAndThrow(final StoredLogService storedLogService, final Throwable throwable, final Logger logger) {
                storeLogToDb(storedLogService, null, throwable);
                handleThrow(throwable, logger);
        }


        /**
         *
         * @param userMessage
         * @param throwable
         * @param logger
         *
         */
        public static void handleStoreAndThrow(final StoredLogService storedLogService, final String userMessage, final Throwable throwable, final Logger logger) {
                storeLogToDb(storedLogService, userMessage, throwable);
                handleThrow(userMessage, throwable, logger);
        }


        /**
         *
         * @param throwable
         */
        public static void handleAndStore(final StoredLogService storedLogService, final Throwable throwable) {
                storeLogToDb(storedLogService, null, throwable);
                handle(throwable);
        }


        /**
         *
         * @param throwable
         * @param logger
         *
         */
        public static void handleAndStore(final StoredLogService storedLogService, final Throwable throwable, final Logger logger) {
                storeLogToDb(storedLogService, null, throwable);
                handle(throwable, logger);
        }


        /**
         *
         * @param userMessage
         * @param throwable
         * @param logger
         *
         */
        public static void handleAndStore(final StoredLogService storedLogService, final String userMessage,
                        final Throwable throwable, final Logger logger) {
                storeLogToDb(storedLogService, userMessage, throwable);
                handle(userMessage, throwable, logger);
        }


        /**
         *
         * @param userMessage
         * @param throwable
         *
         */
        public static void storeLogToDb(final StoredLogService storedLogService, final String userMessage,
                        final Throwable throwable) {
                if (storedLogService != null) {
                        synchronized (ThrowableHandler.class) {
                                StoredLog storedLog = new StoredLog();
                                storedLog.setLogdttm(DateUtils.currentDateTime());
                                storedLog.setLogLevel(LogLevel.ERROR.toString());
                                storedLog.setUserMessage(userMessage);
                                storedLog.setThrowAbleMessage(throwable.getMessage());
                                storedLog.setWhereClass(throwable.getStackTrace()[0].getClassName());


                                storedLogService.save(storedLog);
                        }
                } else {
                        handleLogger.info("StoredLogService is null! You need config, If you want to store logs to DB.");
                }
        }
}