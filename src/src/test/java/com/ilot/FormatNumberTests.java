package com.ilot;


import com.ilot.utils.Utilities;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class FormatNumberTests {

    @Test
    public void when__1000__then_1k() throws ParseException {
        final Float  num       = 1000F;
        final String formatted = Utilities.formatNumber(num);
        assertEquals("1k", formatted);
    }

    @Test
    public void when__102590__then__102_59k() throws ParseException {
        final float  num       = 102590F;
        final String formatted = Utilities.formatNumber(num);
        assertEquals("102.59k", formatted);
    }

    @Test
    public void when__302500890__then__302_5M() throws ParseException {
        final float  num       = 302500890F;
        final String formatted = Utilities.formatNumber(num);
        assertEquals("302.5M", formatted);
    }
}
