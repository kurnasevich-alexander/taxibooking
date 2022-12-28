package eu.senla.taxibooking.util;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;

/** Matcher identifies whether OffsetDateTime instances are almost the same. */
public class OffsetDateTimeAlmostEqualMatcher extends BaseMatcher<OffsetDateTime> {

    private final OffsetDateTime compare;
    private final ChronoUnit unit;
    private final int threshold;

    /** @param compare - instance  for comparison.
     * @param unit - time units.
     * @param threshold - the threshold at which instances are considered equal.*/
    public OffsetDateTimeAlmostEqualMatcher(OffsetDateTime compare, ChronoUnit unit, int threshold) {
        this.compare = compare;
        this.unit = unit;
        this.threshold = threshold;
    }

    @Override
    public boolean matches(Object actual) {
        return unit.between(compare, OffsetDateTime.parse((CharSequence) actual)) < threshold;
    }

    @Override
    public void describeMismatch(Object actual, Description mismatchDescription) {
        mismatchDescription.appendText(actual.toString());
    }


    @Override
    public void describeTo(Description description) {
        description.appendText(compare.toString())
                   .appendText(" ±")
                   .appendText(String.valueOf(threshold))
                   .appendText(" " + unit);
    }
}
