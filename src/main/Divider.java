package main;

import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;
import static java.lang.String.format;
import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.List;

public class Divider {

    private static final int LEFT_PADDING = 2;
    private static final String SPLITTER = "|";
    private static final String HYPHEN = "-";
    private static final String MINUS = "_";
    private static final String SPACE = " ";
    private static final int FIRST_LINES_COUNT = 3;
    private static final int TRAIL_ZERO = 10;
    private static final char OPEN_BRACKET = '(';
    private static final char CLOSE_BRACKET = ')';
    private static final int PERIOD_MAX = 10;
    private static final String SEPARATOR = ".";

    public static String doDivision(int dividend, int divisor) {
        List<String> outPut = new ArrayList<>();

        if (dividend == 0) {
            outPut.add(dividend + " / " + divisor + " = 0");
        } else if (divisor == 0) {
            outPut.add("Error: division by zero!");
        } else {
            StringBuilder result = new StringBuilder();
            outPut = calculateIntegerPart(dividend, divisor);
            if (isFractional(dividend, divisor)) {
                outPut.remove(outPut.size() - 1);
                outPut.addAll(calculatePeriodPart(dividend, divisor, getStartPos(outPut), result));
            }
            formatFirstLines(dividend, divisor, outPut, result);
        }

        return String.join("\n", outPut);
    }

    private static int getStartPos(List<String> list) {
        return list.get(list.size() - 1).length() - 1;
    }

    private static boolean isFractional(int dividend, int divisor) {
        return dividend % divisor != 0;
    }

    private static List<String> calculateIntegerPart(int dividend, int divisor) {
        List<String> calculations = new ArrayList<>();
        int divider = abs(divisor);
        int dividendLength = getNumberLength(dividend);
        String dividendDigits = format("%d", dividend);
        StringBuilder dividendPart = new StringBuilder();

        for (int i = 0; i < dividendLength; i++) {
            char nextDigit = dividendDigits.charAt(i);
            dividendPart.append(nextDigit);
            if (nextDigit != '-') {
                int remainder = abs(parseInt(dividendPart.toString()));

                if (remainder >= divider) {
                    int mod = remainder % divider;
                    calculations.addAll(formatDivision(remainder, divider, i));
                    dividendPart.replace(0, dividendPart.length(), format("%d", mod));
                    remainder = parseInt(dividendPart.toString());
                }

                if (i == dividendLength - 1) {
                    if (abs(dividend) < divider) {
                        calculations.addAll(formatDivision(remainder, divider, i));
                        remainder = abs(dividend);
                    }
                    calculations.add(format("%" + (i + LEFT_PADDING) + "s", valueOf(remainder)));
                }
            }
        }

        return calculations;
    }

    private static List<String> calculatePeriodPart(int dividend, int divisor, int startPos, StringBuilder storage) {
        List<String> calculations = new ArrayList<>();
        List<Integer> remainders = new ArrayList<>();

        storage.append(SEPARATOR);
        int remainder = abs(dividend % divisor);
        remainder *= TRAIL_ZERO;
        int mod = 0;

        for (int i = 0; i < PERIOD_MAX; i++) {
            if (remainder >= abs(divisor)) {
                int div = abs(remainder / divisor);
                mod = abs(remainder % divisor);
                int bracketPos = remainders.indexOf(remainder);

                if (bracketPos == -1) {
                    remainders.add(remainder);
                    storage.append(div);
                } else {
                    storage.insert(bracketPos + 1, OPEN_BRACKET).append(CLOSE_BRACKET);
                    calculations.add(format("%" + (i + startPos + 1) + "s", remainder / TRAIL_ZERO));
                    break;
                }
                calculations.addAll(formatDivision(remainder, divisor, i + startPos));
                if (mod == 0) {
                    calculations.add(format("%" + (i + startPos + LEFT_PADDING) + "s", 0));
                    break;
                }
                if (i == PERIOD_MAX - 1) {
                    calculations.add(format("%" + (i + startPos + LEFT_PADDING) + "s", valueOf(mod)));
                }
                remainder = mod * TRAIL_ZERO;
            } else {
                remainders.add(0);
                storage.append(0);
                remainder *= TRAIL_ZERO;
                if (i == PERIOD_MAX - 1) {
                    calculations.add(format("%" + (i + startPos + 1) + "s", valueOf(mod)));
                }
            }
        }

        return calculations;
    }

    private static List<String> formatDivision(int remainder, int divisor, int offset) {
        List<String> divisionStep = new ArrayList<>();
        int multiply = abs(remainder / divisor * divisor);
        int remainderLength = getNumberLength(remainder);

        divisionStep.add(format("%" + (offset + LEFT_PADDING) + "s", "_" + valueOf(remainder)));
        divisionStep.add(format("%" + (offset + LEFT_PADDING) + "d", multiply));
        divisionStep.add(formatDivLine(offset + LEFT_PADDING - remainderLength, remainderLength));

        return divisionStep;
    }

    private static String formatDivLine(int padding, int count) {
        return repeatSymbol(SPACE, padding) + repeatSymbol(HYPHEN, count);
    }

    private static String repeatSymbol(String symbol, int count) {
        StringBuilder sequence = new StringBuilder();

        for (int i = 0; i < count; i++) {
            sequence.append(symbol);
        }

        return sequence.toString();
    }

    private static int getNumberLength(int number) {
        return valueOf(number).length();
    }

    private static String normalizeResult(int dividend, int divisor, StringBuilder storage) {
        StringBuilder normalizedResult = new StringBuilder();

        if (dividend * divisor < 0) {
            normalizedResult.append("-");
        }

        normalizedResult.append(abs(dividend / divisor));
        if (storage.length() > 0) {
            normalizedResult.append(storage);
        }

        return normalizedResult.toString();
    }

    private static void formatFirstLines(int dividend, int divisor, List<String> outPut, StringBuilder storage) {
        String result = normalizeResult(dividend, divisor, storage);
        String horizontalDivider = fillHorizontalDivider(valueOf(divisor), result);
        int spaces;

        if (dividend / divisor == 0) {
            outPut.subList(0, FIRST_LINES_COUNT).clear();
            spaces = outPut.get(1).length() - (getNumberLength(dividend) + 1);
            outPut.set(0, MINUS + dividend + repeatSymbol(SPACE, spaces) + SPLITTER + divisor);
            outPut.set(1, outPut.get(1) + SPLITTER + horizontalDivider);
            outPut.set(2, outPut.get(2) + SPLITTER + result);
        } else {
            spaces = getNumberLength(dividend) - outPut.get(1).length() + 1;
            outPut.set(0, MINUS + dividend + SPLITTER + divisor);
            outPut.set(1, outPut.get(1) + repeatSymbol(SPACE, spaces) + SPLITTER + horizontalDivider);
            outPut.set(2, outPut.get(2) + repeatSymbol(SPACE, spaces) + SPLITTER + result);
        }
    }

    private static String fillHorizontalDivider(String above, String under) {
        if (above.length() > under.length()) {
            return repeatSymbol(HYPHEN, above.length());
        } else {
            return repeatSymbol(HYPHEN, under.length());
        }
    }

}
