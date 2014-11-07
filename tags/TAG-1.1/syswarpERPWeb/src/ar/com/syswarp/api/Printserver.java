package ar.com.syswarp.api;

import org.apache.log4j.Logger;
import java.io.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;
import javax.print.event.*;

public class Printserver {
	static Logger log = Logger.getLogger(Printserver.class);

	public Printserver() {
		super();
	}

	// todos los metodos.
	public void setDocumento(int copias) {
		DocFlavor flavor = DocFlavor.INPUT_STREAM.POSTSCRIPT;
		PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
		aset.add(MediaSizeName.ISO_A4);
		aset.add(new Copies(copias));
		aset.add(Sides.TWO_SIDED_LONG_EDGE);
		aset.add(Finishings.STAPLE);
		/* locate a print service that can handle it */
		PrintService[] pservices = PrintServiceLookup.lookupPrintServices(
				flavor, aset);
		if (pservices.length > 0) {
			System.out.println("selected printer " + pservices[0].getName());

			/* create a print job for the chosen service */
			DocPrintJob pj = pservices[0].createPrintJob();
			try {
				/*
				 * Create a Doc object to hold the print data. Since the data is
				 * postscript located in a disk file, an input stream needs to
				 * be obtained BasicDoc is a useful implementation that will if
				 * requested close the stream when printing is completed.
				 */
				FileInputStream fis = new FileInputStream("example.ps");
				Doc doc = new SimpleDoc(fis, flavor, null);

				/* print the doc as specified */
				pj.print(doc, aset);

				/*
				 * Do not explicitly call System.exit() when print returns.
				 * Printing can be asynchronous so may be executing in a
				 * separate thread. If you want to explicitly exit the VM, use a
				 * print job listener to be notified when it is safe to do so.
				 */

			} catch (IOException ie) {
				System.err.println(ie);
			} catch (PrintException e) {
				System.err.println(e);
			}

		}
	}
}
