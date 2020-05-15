//
// Swiss QR Bill Generator
// Copyright (c) 2020 Christian Bernasconi
// Copyright (c) 2020 Manuel Bleichenbacher
// Licensed under MIT License
// https://opensource.org/licenses/MIT
//
package net.codecrete.qrbill.generatortest;

import net.codecrete.qrbill.generator.SwicoBillInformation;
import net.codecrete.qrbill.generator.SwicoBillInformation.PaymentCondition;
import net.codecrete.qrbill.generator.SwicoBillInformation.RateDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Swico S1 Encoding")
public class SwicoS1EncodingTest {

    @Test
    public void EncodeExample1() {
        SwicoBillInformation billInfo = SwicoExamples.createExample1();
        String text = billInfo.encodeAsText();
        assertEquals(SwicoExamples.EXAMPLE_1_TEXT, text);
    }

    @Test
    public void EncodeExample2() {
        SwicoBillInformation billInfo = SwicoExamples.createExample2();
        String text = billInfo.encodeAsText();
        assertEquals(SwicoExamples.EXAMPLE_2_TEXT, text);
    }

    @Test
    public void EncodeExample3() {
        SwicoBillInformation billInfo = SwicoExamples.createExample3();
        String text = billInfo.encodeAsText();
        assertEquals(SwicoExamples.EXAMPLE_3_TEXT, text);
    }

    @Test
    public void EncodeExample4() {
        SwicoBillInformation billInfo = SwicoExamples.createExample4();
        String text = billInfo.encodeAsText();
        assertEquals(SwicoExamples.EXAMPLE_4_TEXT, text);
    }

    @Test
    public void EncodeTextWithBackslash() {
        SwicoBillInformation info = new SwicoBillInformation();
        info.setInvoiceNumber("X.66711/8824");
        info.setInvoiceDate(LocalDate.of(2020, 7, 12));
        info.setCustomerReference("MW-2020\\04");
        info.setVatNumber("107978798");
        info.setVatRateDetails(Collections.singletonList(
                new RateDetail(BigDecimal.valueOf(2.5), BigDecimal.valueOf(117.22))
        ));
        info.setPaymentConditions(Arrays.asList(
                new PaymentCondition(BigDecimal.valueOf(3), 5),
                new PaymentCondition(BigDecimal.valueOf(1.5), 20),
                new PaymentCondition(BigDecimal.valueOf(1), 40),
                new PaymentCondition(BigDecimal.ZERO, 60))
        );

        String text = info.encodeAsText();
        assertEquals(
                "//S1/10/X.66711\\/8824/11/200712/20/MW-2020\\\\04/30/107978798/32/2.5:117.22/40/3:5;1.5:20;1:40;0:60",
                text);
    }

    @Test
    public void EncodeEmptyList() {
        SwicoBillInformation info = new SwicoBillInformation();
        info.setInvoiceNumber("10201409");
        info.setInvoiceDate(LocalDate.of(2019, 5, 12));
        info.setCustomerReference("1400.000-53");
        info.setVatNumber("106017086");
        info.setVatDate(LocalDate.of(2018, 5, 8));
        info.setVatRate(BigDecimal.valueOf(7.7));
        info.setVatImportTaxes(new ArrayList<>());
        info.setPaymentConditions(Arrays.asList(
                new PaymentCondition(BigDecimal.valueOf(2), 10),
                new PaymentCondition(BigDecimal.ZERO, 30)));

        String text = info.encodeAsText();
        assertEquals(
                "//S1/10/10201409/11/190512/20/1400.000-53/30/106017086/31/180508/32/7.7/40/2:10;0:30",
                text);
    }
}
