# Swiss QR Bill for Java

Open-source Java library to generate Swiss QR bills (jointly developed with the [.NET version](https://github.com/manuelbl/SwissQRBill.NET)).

Try it yourself and [create a QR bill](https://www.codecrete.net/qrbill). The code for this demonstration (Angular UI and RESTful service) can also be found here. 

**Note: Release 2.0.1-RC1 is available. It implements version 2 of the *Swiss Implementation Guidelines QR-bill* released on November 15, 2019 and the *Style guide* released in January 2019.**

## Introduction

The Swiss QR bill is the new QR code based payment format that will replace the current payment slip starting at 30 June, 2020. The new payment slip will in most cases be sent electronically. But it can still be printed at the bottom of an invoice or added to the invoice on a separate sheet. The payer scans the QR code with his/her mobile banking app to initiate the payment. The payment just needs to be confirmed.

The invoicing party can easily synchronize the received payment with the accounts-receivable accounting as the payment comes with a full set of data including the reference number used on the invoice. The Swiss QR bill is convenient for the payer and payee.

![QR Bill](https://raw.githubusercontent.com/wiki/manuelbl/SwissQRBill/images/qr-invoice-e1.svg?sanitize=true)

*More [examples](https://github.com/manuelbl/SwissQRBill/wiki/Swiss-QR-Invoice-Examples) can be found in the [Wiki](https://github.com/manuelbl/SwissQRBill/wiki)*

## Features

The Swiss QR bill library:

- generates PDF, SVG and PNG files
- generates payment slip (105mm by 210mm), A4 sheets or QR code only
- multilingual: German, French, Italian, English
- validates the invoice data and provides detailed validation information
- parses the invoice data embedded in the QR code
- is easy to use (see example below)
- is small and fast
- is free – even for commecial use (MIT License)
- has only two dependencies (PDFBox and Nayuki's QR code generator)
- is available on Maven Central

## Getting started

The QR bill generator is available at Maven Central. To use it, just add it to your Maven or Gradle project.

If you are using *Maven*, add the below dependency to your `pom.xml`:

    <dependency>
        <groupId>net.codecrete.qrbill</groupId>
        <artifactId>qrbill-generator</artifactId>
        <version>[2.0.1)</version>
    </dependency>

If you are using *Gradle*, add the below dependency to your *build.gradle* file:

    compile group: 'net.codecrete.qrbill', name: 'qrbill-generator', version: '2.0.1+'

To generate a QR bill, you first fill in the `Bill` data structure and then call `QRBill.generate`:

    package net.codecrete.qrbill.examples;
    
    import net.codecrete.qrbill.generator.Address;
    import net.codecrete.qrbill.generator.Bill;
    import net.codecrete.qrbill.generator.QRBill;
    
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;
    
    public class QRBillExample {
    
        public static void main(String[] args) {
    
            // Setup bill
            Bill bill = new Bill();
            bill.setAccount("CH4431999123000889012");
            bill.setAmountFromDouble(199.95);
            bill.setCurrency("CHF");
    
            // Set creditor
            Address creditor = new Address();
            creditor.setName("Robert Schneider AG");
            creditor.setAddressLine1("Rue du Lac 1268/2/22");
            creditor.setAddressLine2("2501 Biel");
            creditor.setCountryCode("CH");
            bill.setCreditor(creditor);
    
            // more bill data
            bill.setReference("210000000003139471430009017");
            bill.setUnstructuredMessage("Abonnement für 2020");
    
            // Set debtor
            Address debtor = new Address();
            debtor.setName("Pia-Maria Rutschmann-Schnyder");
            debtor.setAddressLine1("Grosse Marktgasse 28");
            debtor.setAddressLine2("9400 Rorschach");
            debtor.setCountryCode("CH");
            bill.setDebtor(debtor);
    
            // Generate QR bill
            byte[] svg = QRBill.generate(bill);
    
            // Save QR bill
            Path path = Paths.get("qrbill.svg");
            try {
                Files.write(path, svg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    
            System.out.println("QR bill saved at " + path.toAbsolutePath());
        }
    }

## API Documention

See Javadoc [API Documentation](https://www.codecrete.net/qrbill-javadoc/).

## More information

More information can be found in the [Wiki](https://github.com/manuelbl/SwissQRBill/wiki). It's the joint Wiki for the .NET and the Java version.

## QR Code

For the generation of the QR code itself, [Nayuki's QR code generator](https://github.com/nayuki/QR-Code-generator) is used.

## Other programming languages

A [.NET version](https://github.com/manuelbl/SwissQRBill.NET) of this library is also available. If you are looking for a library for yet another programming language or for a library with professional services, you might want to check out [Services & Tools](https://www.moneytoday.ch/iso20022/movers-shakers/software-hersteller/services-tools/) on [MoneyToday.ch](https://www.moneytoday.ch).
