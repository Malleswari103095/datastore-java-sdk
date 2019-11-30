package com.datastore;

import org.junit.Assert;

public class TestBase {

    public interface CodeBlock {
        void run() throws Exception;
    }

    public TestBase() {
    }

    public void expectIllegalArgException(CodeBlock block, String exceptionMsg, String failMsg) throws Exception {
        this.expectException(block, IllegalArgumentException.class, exceptionMsg, failMsg);
    }

    public void expectException(CodeBlock block, Class<? extends Exception> clazz, String exceptionMsg) throws Exception {
        this.expectException(block, clazz, exceptionMsg, (String)null);
    }

    public void expectException(CodeBlock block, Class<? extends Exception> clazz, String exceptionMsg, String failMsg) throws Exception {
        try {
            block.run();
            if (failMsg == null) {
                failMsg = clazz.getName() + " exception expected";
            }

            Assert.fail(failMsg);
        } catch (Exception var6) {
            if (!clazz.equals(var6.getClass())) {
                throw var6;
            }

            if (exceptionMsg != null && !exceptionMsg.equals(var6.getMessage())) {
                Assert.fail("exception message doesn't match, expected message: " + exceptionMsg + ", actual : " + var6.getMessage());
            }
        }

    }
}
