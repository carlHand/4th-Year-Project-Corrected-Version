package com.microsoftopentechnologies.intellij;

import java.net.MalformedURLException;
import android.content.Context;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;

public class MobileService {

	public static MobileServiceClient getInstance(Context context) throws MalformedURLException {
		return new MobileServiceClient("https://userlogin.azure-mobile.net/", "xMKBRXMxBOveuQJUWcMWfklQKrpNZZ79", context);
	}

}