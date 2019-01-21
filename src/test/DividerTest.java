package test;

import org.junit.Test;

import static java.lang.String.join;
import static main.Divider.doDivision;
import static org.junit.Assert.assertEquals;

class DividerTest {

    @Test
    void testDividerPeriod_DivisionWithFraction() {
        String expected =	"_78459|4"			+ "\n" +
                " 4    |--------"	+ "\n" +
                " -    |19614.75"	+ "\n" +
                "_38"				+ "\n" +
                " 36"				+ "\n" +
                " --"				+ "\n" +
                " _24"				+ "\n" +
                "  24"				+ "\n" +
                "  --"				+ "\n" +
                "   _5"				+ "\n" +
                "    4"				+ "\n" +
                "    -"				+ "\n" +
                "   _19"			+ "\n" +
                "    16"			+ "\n" +
                "    --"			+ "\n" +
                "    _30"			+ "\n" +
                "     28"			+ "\n" +
                "     --"			+ "\n" +
                "     _20"			+ "\n" +
                "      20"			+ "\n" +
                "      --"			+ "\n" +
                "       0";
        assertEquals("division with fraction", join("\n", doDivision(78459, 4)), expected);
    }

    @Test
    void testDividerPeriod_DivisionWithPeriodFraction() {
        String expected =	"_1000|3"		+ "\n" +
                "  9  |-------"	+ "\n" +
                " --  |333.(3)"	+ "\n" +
                " _10"			+ "\n" +
                "   9"			+ "\n" +
                "  --"			+ "\n" +
                "  _10"			+ "\n" +
                "    9"			+ "\n" +
                "   --"			+ "\n" +
                "   _10"		+ "\n" +
                "     9"		+ "\n" +
                "    --"		+ "\n" +
                "     1";
        assertEquals("division with period fraction", join("\n", doDivision(1000, 3)), expected);
    }

    @Test
    void testDividerPeriod_DivisionWithPeriodFractionSmallerDividend() {
        String expected =	"_7 |12"		+ "\n" +
                " 60|-------"	+ "\n" +
                " --|0.58(3)"	+ "\n" +
                "_100"			+ "\n" +
                "  96"			+ "\n" +
                " ---"			+ "\n" +
                "  _40"			+ "\n" +
                "   36"			+ "\n" +
                "   --"			+ "\n" +
                "    4";
        assertEquals("division with period fraction (smaller dividend)", join("\n", doDivision(7, 12)), expected);
    }

    @Test
    void testDividerPeriod_DivisionWithPeriodFractionMoreDigits() {
        String expected =	"_25 |39"			+ "\n" +
                " 234|----------"	+ "\n" +
                " ---|0.(641025)"	+ "\n" +
                " _160"				+ "\n" +
                "  156"				+ "\n" +
                "  ---"				+ "\n" +
                "   _40"			+ "\n" +
                "    39"			+ "\n" +
                "    --"			+ "\n" +
                "    _100"			+ "\n" +
                "      78"			+ "\n" +
                "     ---"			+ "\n" +
                "     _220"			+ "\n" +
                "      195"			+ "\n" +
                "      ---"			+ "\n" +
                "       25";
        assertEquals("division with period fraction (more digits)", join("\n", doDivision(25, 39)), expected);
    }

    @Test
    void testDividerPeriod_NegativeDivisionWithPeriodFractionMoreDigits() {
        String expected =	"_25 |-39"			+ "\n" +
                " 234|-----------"	+ "\n" +
                " ---|-0.(641025)"	+ "\n" +
                " _160"				+ "\n" +
                "  156"				+ "\n" +
                "  ---"				+ "\n" +
                "   _40"			+ "\n" +
                "    39"			+ "\n" +
                "    --"			+ "\n" +
                "    _100"			+ "\n" +
                "      78"			+ "\n" +
                "     ---"			+ "\n" +
                "     _220"			+ "\n" +
                "      195"			+ "\n" +
                "      ---"			+ "\n" +
                "       25";
        assertEquals("negative division with period fraction (more digits)", join("\n", doDivision(25, -39)), expected);
    }

    @Test
    void testDividerPeriod_DivisorShouldBeNonZero() {
        assertEquals("division by zero", join("\n", doDivision(1, 0)), "Error: division by zero!");
    }

    @Test
    void testDividerPeriod_ZeroDividendShouldReturnZero() {
        assertEquals("zero division", join("\n", doDivision(0, 1)), "0 / 1 = 0");
    }

    @Test
    void testDividerPeriod_DivisionShouldBeDoneWithoutRemainder() {
        String expected =	"_132|3"	+ "\n" +
                " 12 |--"	+ "\n" +
                " -- |44"	+ "\n" +
                " _12"		+ "\n" +
                "  12"		+ "\n" +
                "  --"		+ "\n" +
                "   0";
        assertEquals("division without remainder", join("\n", doDivision(132, 3)), expected);
    }

    @Test
    void testDividerPeriod_DividendNegative() {
        String expected =	"_-132|3"	+ "\n" +
                "  12 |---"	+ "\n" +
                "  -- |-44"	+ "\n" +
                "  _12"		+ "\n" +
                "   12"		+ "\n" +
                "   --"		+ "\n" +
                "    0";
        assertEquals("dividend negative", join("\n", doDivision(-132, 3)), expected);
    }

    @Test
    void testDividerPeriod_DividendAndDivisorNegative() {
        String expected =	"_-132|-3"	+ "\n" +
                "  12 |--"	+ "\n" +
                "  -- |44"	+ "\n" +
                "  _12"		+ "\n" +
                "   12"		+ "\n" +
                "   --"		+ "\n" +
                "    0";
        assertEquals("dividend and divisor negative", join("\n", doDivision(-132, -3)), expected);
    }

    @Test
    void testDividerPeriod_DivisorNegative() {
        String expected =	"_132|-3"	+ "\n" +
                " 12 |---"	+ "\n" +
                " -- |-44"	+ "\n" +
                " _12"		+ "\n" +
                "  12"		+ "\n" +
                "  --"		+ "\n" +
                "   0";
        assertEquals("divisor negative", join("\n", doDivision(132, -3)), expected);
    }

    @Test
    void testDividerPeriod_FindMaxFractionalPart() {
        String expected =	"_5056|89"				+ "\n" +
                " 445 |-------------"	+ "\n" +
                " --- |56.8089887640"	+ "\n" +
                " _606"					+ "\n" +
                "  534"					+ "\n" +
                "  ---"					+ "\n" +
                "  _720"				+ "\n" +
                "   712"				+ "\n" +
                "   ---"				+ "\n" +
                "    _800"				+ "\n" +
                "     712"				+ "\n" +
                "     ---"				+ "\n" +
                "     _880"				+ "\n" +
                "      801"				+ "\n" +
                "      ---"				+ "\n" +
                "      _790"			+ "\n" +
                "       712"			+ "\n" +
                "       ---"			+ "\n" +
                "       _780"			+ "\n" +
                "        712"			+ "\n" +
                "        ---"			+ "\n" +
                "        _680"			+ "\n" +
                "         623"			+ "\n" +
                "         ---"			+ "\n" +
                "         _570"			+ "\n" +
                "          534"			+ "\n" +
                "          ---"			+ "\n" +
                "          _360"		+ "\n" +
                "           356"		+ "\n" +
                "           ---"		+ "\n" +
                "             4";
        assertEquals("find max fractional part", join("\n", doDivision(5056, 89)), expected);
    }
}