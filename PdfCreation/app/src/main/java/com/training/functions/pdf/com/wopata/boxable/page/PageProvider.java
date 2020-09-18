package com.training.functions.pdf.com.wopata.boxable.page;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public interface PageProvider<T extends PDPage> {

	T createPage();
	
	PDDocument getDocument();
}
