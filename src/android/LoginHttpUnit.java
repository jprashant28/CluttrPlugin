package com.cluttr.post;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.xml.sax.SAXException;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.protocol.UploadFileSpec;

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
		Item item = new Item();
		
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

class Item {
	public String 
		title = "Original 1965 Piston Cup",
		asking = "500",
		location = "Radiator Springs in Carburetor County",
		postal_code = "56666",
		description = "A Piston Cup championship consists of various races where the racers are dotted with an specific number of points according to their positions on each race during the championship."
		;
	
	//commenting the images so that it works with phongap for now.
	public String[] imageurls = {
                                  //"http://lh3.googleusercontent.com/_zhGQqZy6xK3mzsEeqIJb-9hV7y435-VP2qJmwpyV6XAdmjfCYroKpXxO28XuzcHUtxefkkvVBJPoson7qmJgA=s1024",
                                  "http://img12.shop-pro.jp/PA01065/037/product/59533789.jpg?20130524194316",
                                  "http://vignette1.wikia.nocookie.net/pixarcars/images/6/6e/From_This_Movie_Cars.jpg/revision/latest?cb=20140206225406"
								};
}
