package br.com.totvs.raas.product.commad.test.port.adapter.shared;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class WaitTest {

    @Test
    public void shouldReturnOK() {
        String actual = Wait.notNull(() -> "OK");
        assertEquals("OK", actual);
    }

    @Test
    public void shouldReturnNull() {
        String actual = Wait.notNull(() -> null);
        assertNull(actual);
    }

}