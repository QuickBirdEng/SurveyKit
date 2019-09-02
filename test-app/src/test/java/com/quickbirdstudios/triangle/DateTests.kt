package com.quickbirdstudios.triangle

import com.quickbirdstudios.triangle.service.DateService
import org.junit.Assert
import org.junit.Test
import java.util.*
import java.util.Calendar.*

class DateTest {

    private val timeZoneGer = TimeZone.getTimeZone("Europe/Berlin")
    private val localeGer = Locale("de")
    private val dateServiceGer = DateService(timeZoneGer, localeGer)

    private val timeZoneEng = TimeZone.getTimeZone("Europe/London")
    private val localeEng = Locale("en")
    private val dateServiceEng = DateService(timeZoneEng, localeEng)

    @Test
    fun beginningOfTheYearTests1() {
        startOfTheWeekTest_Jan2019(dateServiceGer)
        startOfTheWeekTest_Jan2019(dateServiceEng, -1, 1)
    }

    @Test
    fun beginningOfTheYearTests2() {
        endOfTheWeekTest_Jan2019(dateServiceGer)
        endOfTheWeekTest_Jan2019(dateServiceEng, -1, 1)
    }

    @Test
    fun startOfTheWeek_endOfYearTests1() {
        startOfTheWeekTest_Dec2020(dateServiceGer)
        startOfTheWeekTest_Dec2020(dateServiceEng, -1, 1)
    }

    @Test
    fun startOfTheWeek_endOfYearTests2() {
        endOfTheWeekTest_Dec2020(dateServiceGer)
        endOfTheWeekTest_Dec2020(dateServiceEng, -1, 1)
    }

    @Test
    fun startOfMonthTests() {
        startOfMonthJanuary(dateServiceGer)
    }

    @Test
    fun endOfMonthTests() {
        endOfMonthJanuary(dateServiceGer)
        endOfMonthFebruary(dateServiceGer)
        endOfMonthJanuary(dateServiceEng, 1)
        endOfMonthFebruary(dateServiceEng, 1)
    }

    @Test
    fun endOfMonthLeapYearTest() {
        endOfMonthLeapYear2020FebruaryTest(dateServiceGer)
        endOfMonthLeapYear2020FebruaryTest(dateServiceEng, 1)
    }

    @Test
    fun daysInBetween_onSameDay() {
        val firstOfJanuary = Calendar.getInstance().apply { set(2019, 1, 1) }.time
        val firstOfJanuaryEvening = Calendar.getInstance().apply { set(2019, 1, 1, 8, 0, 0) }.time

        Assert.assertEquals(0, dateServiceEng.daysInBetween(firstOfJanuary, firstOfJanuaryEvening))
        Assert.assertEquals(0, dateServiceGer.daysInBetween(firstOfJanuary, firstOfJanuaryEvening))
    }

    @Test
    fun daysInBetween_onDifferentDays() {
        val firstOfJanuary = Calendar.getInstance().apply { set(2019, 1, 1) }.time
        val secondOfJanuary = Calendar.getInstance().apply { set(2019, 1, 2) }.time

        Assert.assertEquals(1, dateServiceEng.daysInBetween(firstOfJanuary, secondOfJanuary))
        Assert.assertEquals(1, dateServiceGer.daysInBetween(firstOfJanuary, secondOfJanuary))
    }

    //region Private Api

    private fun startOfTheWeekTest_Jan2019(
        dateService: DateService, dayOffset: Int = 0, hourOffset: Int = 0
    ) {
        val referenceDate = getInstance().apply {
            set(2019, 0, 7 + dayOffset, 0 + hourOffset, 0, 0)
            set(MILLISECOND, 0)
        }.time
        val cal = getInstance()

        assertSameStartWeek(
            dateService = dateService,
            referenceDate = referenceDate,
            dates = listOf(
                cal.january2019DayAsDate(7 + dayOffset),
                cal.january2019DayAsDate(8 + dayOffset),
                cal.january2019DayAsDate(9 + dayOffset),
                cal.january2019DayAsDate(11 + dayOffset),
                cal.january2019DayAsDate(12 + dayOffset),
                cal.january2019DayAsDate(13 + dayOffset)
            )
        )
    }

    private fun endOfTheWeekTest_Jan2019(
        dateService: DateService,
        dayOffset: Int = 0,
        hourOffset: Int = 0
    ) {
        val referenceDate = getInstance().apply {
            set(2019, 0, 13 + dayOffset, 23 + hourOffset, 59, 59)
            set(MILLISECOND, 999)
        }.time
        val cal = getInstance()

        assertSameEndWeek(
            dateService = dateService,
            referenceDate = referenceDate,
            dates = listOf(
                cal.january2019DayEndAsDate(7 + dayOffset),
                cal.january2019DayEndAsDate(8 + dayOffset),
                cal.january2019DayEndAsDate(9 + dayOffset),
                cal.january2019DayEndAsDate(11 + dayOffset),
                cal.january2019DayEndAsDate(12 + dayOffset),
                cal.january2019DayEndAsDate(13 + dayOffset)
            )
        )
    }

    private fun startOfTheWeekTest_Dec2020(
        dateService: DateService, dayOffset: Int = 0, hourOffset: Int = 0
    ) {
        val referenceDate = getInstance().apply {
            set(2019, 11, 30 + dayOffset, 0 + hourOffset, 0, 0)
            set(MILLISECOND, 0)
        }.time
        val cal = getInstance()

        assertSameStartWeek(
            dateService = dateService,
            referenceDate = referenceDate,
            dates = listOf(
                cal.december2019DayAsDate(30 + dayOffset),
                cal.december2019DayAsDate(31 + dayOffset),
                cal.january2020DayAsDate(2 + dayOffset),
                cal.january2020DayAsDate(3 + dayOffset),
                cal.january2020DayAsDate(4 + dayOffset),
                cal.january2020DayAsDate(5 + dayOffset)
            )
        )
    }

    private fun endOfTheWeekTest_Dec2020(
        dateService: DateService, dayOffset: Int = 0, hourOffset: Int = 0
    ) {
        val referenceDate = getInstance().apply {
            set(2020, 0, 5 + dayOffset, 23 + hourOffset, 59, 59)
            set(MILLISECOND, 999)
        }.time

        val cal = getInstance()
        assertSameEndWeek(
            dateService = dateService,
            referenceDate = referenceDate,
            dates = listOf(
                cal.december2019DayEndAsDate(30 + dayOffset),
                cal.december2019DayEndAsDate(31 + dayOffset),
                cal.january2020DayEndAsDate(1 + dayOffset),
                cal.january2020DayEndAsDate(2 + dayOffset),
                cal.january2020DayEndAsDate(3 + dayOffset),
                cal.january2020DayEndAsDate(4 + dayOffset)
            )
        )
    }

    private fun startOfMonthJanuary(dateService: DateService, hourOffset: Int = 0) {
        val referenceDate = getInstance().apply {
            set(2019, 0, 1, 0 + hourOffset, 0, 0)
            set(MILLISECOND, 0)
        }.time

        val cal = getInstance()
        val monday7th = cal.january2019DayAsDate(7)
        assertDateEquality(referenceDate, dateService.startOfMonth(monday7th), monday7th)
    }

    private fun endOfMonthJanuary(dateService: DateService, hourOffset: Int = 0) {
        val referenceDate = getInstance().apply {
            set(2019, 0, 31, 23 + hourOffset, 59, 59)
            set(MILLISECOND, 999)
        }.time

        val cal = getInstance()
        val monday7th = cal.january2019DayEndAsDate(7)
        assertDateEquality(referenceDate, dateService.endOfMonth(monday7th), monday7th)
    }

    private fun endOfMonthFebruary(dateService: DateService, hourOffset: Int = 0) {
        val referenceDate = getInstance().apply {
            set(2019, 1, 28, 23 + hourOffset, 59, 59)
            set(MILLISECOND, 999)
        }.time

        val cal = getInstance()
        val monday7th = cal.february2019DayEndAsDate(7)
        val thursday28th = cal.february2019DayEndAsDate(28)
        assertDateEquality(referenceDate, dateService.endOfMonth(monday7th), monday7th)
        assertDateEquality(referenceDate, dateService.endOfMonth(thursday28th), thursday28th)
    }

    private fun endOfMonthLeapYear2020FebruaryTest(dateService: DateService, hourOffset: Int = 0) {
        val referenceDate = getInstance().apply {
            set(2020, 1, 29, 23 + hourOffset, 59, 59)
            set(MILLISECOND, 999)
        }.time

        val cal = getInstance()
        val monday7th = cal.february2020DayEndAsDate(7)
        val friday28th = cal.february2020DayEndAsDate(28)
        val saturday29th = cal.february2020DayEndAsDate(29)
        assertDateEquality(referenceDate, dateService.endOfMonth(monday7th), monday7th)
        assertDateEquality(referenceDate, dateService.endOfMonth(friday28th), friday28th)
        assertDateEquality(referenceDate, dateService.endOfMonth(saturday29th), saturday29th)
    }

    private fun Calendar.january2019DayAsDate(day: Int) =
        apply { set(2019, 0, day) }.randomizeFields()

    private fun Calendar.january2019DayEndAsDate(day: Int) =
        apply { set(2019, 0, day) }.endOfDayFields()

    private fun Calendar.february2019DayEndAsDate(day: Int) =
        apply { set(2019, 1, day) }.endOfDayFields()

    private fun Calendar.december2019DayAsDate(day: Int) =
        apply { set(2019, 11, day) }.randomizeFields()

    private fun Calendar.december2019DayEndAsDate(day: Int) =
        apply { set(2019, 11, day) }.endOfDayFields()

    private fun Calendar.january2020DayAsDate(day: Int) =
        apply { set(2020, 0, day) }.randomizeFields()

    private fun Calendar.january2020DayEndAsDate(day: Int) =
        apply { set(2020, 0, day) }.endOfDayFields()

    private fun Calendar.february2020DayEndAsDate(day: Int) =
        apply { set(2020, 1, day) }.endOfDayFields()

    private fun Calendar.randomizeFields(): Date {
        this.set(
            get(YEAR),
            get(MONTH),
            get(DATE),
            Math.floor(1 + (Math.random() * 23)).toInt(),
            Math.floor(Math.random() * 60).toInt(),
            Math.floor(Math.random() * 60).toInt()
        )
        return this.time
    }

    private fun Calendar.endOfDayFields(): Date {
        this.set(
            get(YEAR),
            get(MONTH),
            get(DATE),
            23,
            59,
            59
        )
        this.set(MILLISECOND, 999)
        return this.time
    }

    private fun assertSameEndWeek(
        dateService: DateService,
        referenceDate: Date,
        dates: List<Date>
    ) {
        assertDatesEqual(
            expected = referenceDate,
            actual = dates.map(dateService::endOfWeek),
            originals = dates
        )
    }

    private fun assertSameStartWeek(
        dateService: DateService,
        referenceDate: Date,
        dates: List<Date>
    ) {
        assertDatesEqual(
            expected = referenceDate,
            actual = dates.map(dateService::startOfWeek),
            originals = dates
        )
    }

    private fun assertDatesEqual(expected: Date, actual: List<Date>, originals: List<Date>) {
        for (i in 0 until actual.size) {
            assertDateEquality(expected, actual[i], originals[i])
        }
    }


    private fun assertDateEquality(d1: Date, d2: Date, original: Date) {
        Assert.assertEquals("Original: $original", d1, d2)
        Assert.assertEquals("Original: $original", d1.time, d2.time)
        Assert.assertEquals("Original: $original", d1.day, d2.day)
    }

    //endregion
}
