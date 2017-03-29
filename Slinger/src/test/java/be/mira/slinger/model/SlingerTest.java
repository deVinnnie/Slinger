package be.mira.slinger.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SlingerTest {

    @Test
    public void testBerekenValversnellingGivesCorrectResult(){
        double g = Slinger.berekenValversnelling(2.0060, 1);
        assertEquals(9.81, g, 0.01);
    }

    @Test
    public void testBerekenValversnellingWithLengthZeroGivesZero(){
        double g = Slinger.berekenValversnelling(10, 0);
        assertEquals(0, g, 0);
    }

    @Test
    public void testBerekenValversnellingWithNegativeLengthGivesZero(){
        double g = Slinger.berekenValversnelling(10, -10);
        assertEquals(0, g, 0);
    }

    @Test
    public void testBerekenValversnellingWithPeriodZeroGivesZero(){
        double g = Slinger.berekenValversnelling(0, 10);
        assertEquals(0, g, 0);
    }

    @Test
    public void testBerekenValversnellingWithNegativePeriodGivesZero(){
        double g = Slinger.berekenValversnelling(-10, 10);
        assertEquals(0, g, 0);
    }
}
