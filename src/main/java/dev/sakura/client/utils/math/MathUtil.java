package dev.sakura.client.utils.math;

import dev.sakura.client.utils.vector.Vector3d;
import net.minecraft.util.math.MathHelper;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.concurrent.ThreadLocalRandom;

public class MathUtil {
    public static final float PI = (float) Math.PI;
    public static final float TO_RADIANS = PI / 180.0F;
    public static final float TO_DEGREES = 180.0F / PI;

    public static final float[] COSINE = new float[361];
    public static final float[] SINE = new float[361];

    static {
        for (int i = 0; i <= 360; ++i) {
            COSINE[i] = MathHelper.cos(i * TO_RADIANS);
            SINE[i] = MathHelper.sin(i * TO_RADIANS);
        }
    }

    public static float getClosestMultipleOfDivisor(final float valueToRound, final float divisor) {
        final float quotient = Math.round(valueToRound / divisor);
        return divisor * quotient;
    }

    public static double round(final double value, final int scale, final double inc) {
        final double halfOfInc = inc / 2.0;
        final double floored = Math.floor(value / inc) * inc;

        if (value >= floored + halfOfInc) {
            return new BigDecimal(Math.ceil(value / inc) * inc)
                    .setScale(scale, RoundingMode.HALF_UP)
                    .doubleValue();
        } else {
            return new BigDecimal(floored)
                    .setScale(scale, RoundingMode.HALF_UP)
                    .doubleValue();
        }
    }

    public static double roundWithSteps(final double value, final double steps) {
        double a = ((Math.round(value / steps)) * steps);
        a *= 1000;
        a = (int) a;
        a /= 1000;
        return a;
    }

    public static double getDistance(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2) {
        final double d0 = x2 - x1;
        final double d1 = y2 - y1;
        final double d2 = z2 - z1;
        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public static double wrappedDifference(double number1, double number2) {
        return Math.min(Math.abs(number1 - number2), Math.min(Math.abs(number1 - 360) - Math.abs(number2 - 0), Math.abs(number2 - 360) - Math.abs(number1 - 0)));
    }

    public static double getAngleBetweenLocations(Vector3d location1, Vector3d location2) {
        double deltaX = location2.x - location1.x;
        double deltaZ = location2.z - location1.z;
        double yawToLocation = Math.toDegrees(Math.atan2(deltaZ, deltaX)) - 90;
        return MathHelper.wrapDegrees((float) yawToLocation);
    }

    public static float lerp(final float a, final float b, final float c) {
        return a + c * (b - a);
    }

    public static double lerp(double old, double newVal, double amount) {
        return (1.0 - amount) * old + amount * newVal;
    }

    public static double interpolateReverse(final double current, final double previous, final double multiplier) {
        return previous + (current - previous) * multiplier;
    }

    public static float interpolate(final float current, final float previous, final float multiplier) {
        return previous + (current - previous) * multiplier;
    }

    public static Double interpolate(double oldValue, double newValue, double interpolationValue) {
        return (oldValue + (newValue - oldValue) * interpolationValue);
    }

    public static float interpolateFloat(float oldValue, float newValue, double interpolationValue) {
        return interpolate(oldValue, newValue, (float) interpolationValue);
    }

    public static int interpolateInt(int oldValue, int newValue, double interpolationValue) {
        return (int) interpolate(oldValue, newValue, (float) interpolationValue);
    }

    public static Vector3d interpolate(final Vector3d currentVector, final Vector3d previousVector, final double multiplier) {
        return new Vector3d(
                interpolateReverse(currentVector.getX(), previousVector.getX(), multiplier),
                interpolateReverse(currentVector.getY(), previousVector.getY(), multiplier),
                interpolateReverse(currentVector.getZ(), previousVector.getZ(), multiplier)
        );
    }

    public static double linearInterpolate(double min, double max, double norm) {
        return (max - min) * norm + min;
    }

    // --- Clamps ---

    public static float clamp(float num, float min, float max) {
        return num < min ? min : Math.min(num, max);
    }

    public static double clamp(double num, double min, double max) {
        return num < min ? min : Math.min(num, max);
    }

    public static int clamp(int num, int min, int max) {
        return num < min ? min : Math.min(num, max);
    }

    // --- Randoms ---

    public static int getRandom(int min, int max) {
        if (min == max) return min;
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static double getRandom(double min, double max) {
        if (min == max) return min;
        if (min > max) {
            double temp = min;
            min = max;
            max = temp;
        }
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static float getRandom(float min, float max) {
        if (min == max) return min;
        if (min > max) {
            float temp = min;
            min = max;
            max = temp;
        }
        // ThreadLocalRandom doesn't have nextFloat(min, max) directly usually, but nextDouble works
        return (float) ThreadLocalRandom.current().nextDouble(min, max);
    }

    // --- Helpers ---

    public static double incValue(double val, double inc) {
        double one = 1.0 / inc;
        return Math.round(val * one) / one;
    }

    public static boolean approximatelyEquals(float a, float b) {
        return Math.abs(b - a) < 1.0E-5F;
    }

    public static float calculateGaussianValue(float x, float sigma) {
        double output = 1.0 / Math.sqrt(2.0 * Math.PI * (sigma * sigma));
        return (float) (output * Math.exp(-(x * x) / (2.0 * (sigma * sigma))));
    }

    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }

    public static double round(double num, double increment) {
        BigDecimal bd = new BigDecimal(num);
        bd = (bd.setScale((int) increment, RoundingMode.HALF_UP));
        return bd.doubleValue();
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static String round(String value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.stripTrailingZeros();
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.toString();
    }

    public static double wrapAngleTo180_double(double value) {
        value = value % 360.0D;

        if (value >= 180.0D) {
            value -= 360.0D;
        }

        if (value < -180.0D) {
            value += 360.0D;
        }

        return value;
    }

    public static int getNumberOfDecimalPlace(double value) {
        final BigDecimal bigDecimal = new BigDecimal(value);
        return Math.max(0, bigDecimal.stripTrailingZeros().scale());
    }

    public static double roundToDecimalPlace(double value, double inc) {
        final double halfOfInc = inc / 2.0D;
        final double floored = StrictMath.floor(value / inc) * inc;
        if (value >= floored + halfOfInc)
            return new BigDecimal(StrictMath.ceil(value / inc) * inc, MathContext.DECIMAL64).
                    stripTrailingZeros()
                    .doubleValue();
        else
            return new BigDecimal(floored, MathContext.DECIMAL64)
                    .stripTrailingZeros()
                    .doubleValue();
    }
}
