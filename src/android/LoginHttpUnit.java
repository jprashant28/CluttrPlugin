package com.cluttr.post;

//import java.awt.image.BufferedImage; // not supported by phonegap
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

//import javax.imageio.ImageIO; // not supported by phonegap

import org.xml.sax.SAXException;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.protocol.UploadFileSpec;

import com.cluttr.post.Item;

public class LoginHttpUnit {

	public static void main(String[] args) throws IOException, SAXException {
		post();
	}

	public static void post() throws IOException, SAXException {
		WebForm form;
		WebResponse resp;

		String 
			cityCode = "aus", 	// austin
			section = "fso",  	// for sale by owner. fsd = for sale by dealer
			category = "5"	 	// 5='general for sale'. or pick from a huge list
			;

		HttpUnitOptions.setScriptingEnabled( false ); 
		com.cluttr.post.Item item = new com.cluttr.post.Item();
		
		WebConversation wc = new WebConversation();
		resp = wc.getResponse( "https://accounts.craigslist.org/" ); // read this page

		// Login
		form = resp.getForms()[0];
		form.setParameter("inputEmailHandle", "ravi11@hotmail.com");
		form.setParameter("inputPassword", "clutter123");
		resp = form.submit();

		// Post under the user city
		form = resp.getForms()[1];
		form.setParameter("areaabb", cityCode); // dropdown city code
		resp = form.submit();

		// Select posting section
		form = resp.getForms()[0];
		form.setParameter("id", section); // radio button category
		resp = form.submit();

		// Select item category
		form = resp.getForms()[0];
		form.setParameter("id", category); // radio button category
		resp = form.submit();
		
		// Fill out form details
		form = resp.getForms()[0];
		form.setParameter("PostingTitle", item.title);
		form.setParameter("Ask", item.asking);
		form.setParameter("GeographicArea", item.location);
		form.setParameter("postal", item.postal_code);
		form.setParameter("PostingBody", item.description);
		//form.setParameter("language", "5"); // english
		resp = form.submit();
		
		// Upload one image at a time
		//commenting it out so we can compile on Phonegap
	
		SubmitButton button = null;
	/*	
		for(String imageurl : item.imageurls) {
			form = resp.getForms()[0];
			ArrayList<UploadFileSpec> uploads = new ArrayList<UploadFileSpec>();
			URL url = new URL(imageurl);
			BufferedImage bi = ImageIO.read(url);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(bi, "png", os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			UploadFileSpec uploadFileSpec = new UploadFileSpec("imgfile", is, "image/png");
			uploads.add(uploadFileSpec);
			form.setParameter("file", uploads.toArray(new UploadFileSpec[0]));
			button = form.getSubmitButton("go");
			resp = form.submit(button);
		}

		// Done with images
		// No of forms increase with images
		int form_number = item.imageurls.length + 2;
		form = resp.getForms()[form_number];
		button = form.getSubmitButton("go");
		resp = form.submit(button);
		*/

		// Publish
		form = resp.getForms()[0];
		button = form.getSubmitButton("go");
		resp = form.submit(button);

		System.out.println(resp.getText());
	}
}
