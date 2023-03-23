package com.hegemony.arraymi.captcha.util;

public class EmailTemplates {

	public static final String APP_NAME = "########APP_NAME########";
	public static final String APP_URL = "########HOST_URL########";
	public static final String NAME = "########NAME########";
	public static final String VERIFY_EMAIL_URL = "########EMAIL_VERIFICATION_LINK########";
	public static final String AMOUNT = "########AMOUNT########";
	public static final String FINAL_AMOUNT = "########FINAL_AMOUNT########";
	public static final String CURRENCY = "########CURRENCY########";
	public static final String CAPTCHA_COUNT = "########CAPTCHA_COUNT########";
	public static final String TRAN_REF = "########TRAN_REF########";
	public static final String PAY_PAL_ADDRESS = "########PAY_PAL_ADDRESS########";
	public static final String PROFILE_ID = "########PROFILE_ID########";
	public static final String EMAIL_ID = "########EMAIL_ID########";
	public static final String PASSWORD = "########PASSWORD########";
	public static final String SUPPORT_MAIL = "########SUPPORT_MAIL########";

	private static final String STYLE = "<style>/* You can add global styles to this file, and also import other style files */\r\n"
			+ "/* @import url(../node_modules/bootstrap/dist/css/bootstrap.min.css) */\r\n"
			+ "/* @import url(https://unpkg.com/bootstrap@4.1.0/dist/css/bootstrap.min.css) */\r\n"
			+ ".page-container {\r\n" + "    min-height: 86vh;\r\n" + "}\r\n" + ".page-header {\r\n"
			+ "    min-height: 10vh;\r\n" + "}\r\n" + ".page-footer {\r\n" + "    min-height: 4vh;\r\n" + "}\r\n"
			+ "input:invalid {\r\n" + "    border-color: #8b0000;\r\n" + "    border-width: 3px;\r\n" + "}\r\n" + "\r\n"
			+ "/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbIndlYnBhY2s6Ly9zcmMvc3R5bGVzLmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQSw4RUFBOEU7QUFDOUUsc0VBQXNFO0FBQ3RFLDhFQUE4RTtBQUM5RTtJQUNJLGdCQUFnQjtBQUNwQjtBQUVBO0lBQ0ksZ0JBQWdCO0FBQ3BCO0FBRUE7SUFDSSxlQUFlO0FBQ25CO0FBRUE7SUFDSSxxQkFBcUI7SUFDckIsaUJBQWlCO0FBQ3JCIiwic291cmNlc0NvbnRlbnQiOlsiLyogWW91IGNhbiBhZGQgZ2xvYmFsIHN0eWxlcyB0byB0aGlzIGZpbGUsIGFuZCBhbHNvIGltcG9ydCBvdGhlciBzdHlsZSBmaWxlcyAqL1xuLyogQGltcG9ydCB1cmwoLi4vbm9kZV9tb2R1bGVzL2Jvb3RzdHJhcC9kaXN0L2Nzcy9ib290c3RyYXAubWluLmNzcykgKi9cbi8qIEBpbXBvcnQgdXJsKGh0dHBzOi8vdW5wa2cuY29tL2Jvb3RzdHJhcEA0LjEuMC9kaXN0L2Nzcy9ib290c3RyYXAubWluLmNzcykgKi9cbi5wYWdlLWNvbnRhaW5lciB7XG4gICAgbWluLWhlaWdodDogODZ2aDtcbn1cblxuLnBhZ2UtaGVhZGVyIHtcbiAgICBtaW4taGVpZ2h0OiAxMHZoO1xufVxuXG4ucGFnZS1mb290ZXIge1xuICAgIG1pbi1oZWlnaHQ6IDR2aDtcbn1cblxuaW5wdXQ6aW52YWxpZCB7XG4gICAgYm9yZGVyLWNvbG9yOiAjOGIwMDAwO1xuICAgIGJvcmRlci13aWR0aDogM3B4O1xufVxuIl0sInNvdXJjZVJvb3QiOiIifQ== */</style>"
			+ "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css\" integrity=\"sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh\" crossorigin=\"anonymous\">\r\n"
			+ "<script src=\"https://code.jquery.com/jquery-3.4.1.slim.min.js\" integrity=\"sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n\" crossorigin=\"anonymous\"></script>\r\n"
			+ "<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\" integrity=\"sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo\" crossorigin=\"anonymous\"></script>\r\n"
			+ "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js\" integrity=\"sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6\" crossorigin=\"anonymous\"></script>";

	private static final String HEAD = "<html>\r\n<head>\r\n" + "    <meta charset=\"utf-8\">\r\n"
			+ "    <title>ARM Captcha</title>\r\n" + "    <base href=\"/\">\r\n"
			+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n"
			+ "    <link rel=\"icon\" type=\"image/x-icon\" href=\"favicon.ico\">\r\n"
			+ "    <link href=\"https://fonts.googleapis.com/css?family=Roboto:300,400,500&amp;display=swap\" rel=\"stylesheet\">\r\n"
			+ STYLE + "</head>\r\n";

	public static final String FORGET_PASSWORD_TEMPLATE = "<html>\r\n" + "<body>\r\n"
			+ "	<h1>Greetings from ${appName}</h1>\r\n" + "	Dear ${name},\r\n" + "	<br />\r\n" + "	<br />\r\n"
			+ "	We have changed your password. Please use updated password to login next time.\r\n" + "	<p>\r\n"
			+ "	<b>Your login details :</b>\r\n" + "<div class=\"container\">" + "	<br />\r\n"
			+ "	\tProfile ID : ${profileId}\r\n" + "	<br />\r\n" + "	\tEmail ID : ${userEmail}\r\n" + "	<br />\r\n"
			+ "	\tPassword : ${password}\r\n" + "	<br />\r\n" + "	<br />\r\n" + "</div>"
			+ "	We suggest you to change the password for security purpose.\r\n" + "	<br />\r\n"
			+ "	You can log in using username or email address.\r\n" + "	<br />\r\n" + "	</p>\r\n" + "	<p>\r\n"
			+ "	Thanks,\r\n" + "	<br />\r\n" + "The	${appName} Team\r\n" + "	<br />\r\n" + "	</p>\r\n"
			+ "	</body>\r\n" + "</html>";

//	TODO finalize all these templates
	public static final String CUSTOMER_QUERY_CONFIRMATION_TEMPLATE = HEAD + "  <body>\r\n"
			+ "  <div _ngcontent-dtw-c54=\"\" class=\"container\"><div _ngcontent-dtw-c54=\"\" class=\"row bg-primary justify-content-center\"><div _ngcontent-dtw-c54=\"\" class=\"text-center\"><img _ngcontent-dtw-c54=\"\" alt=\"ARM Captcha\" src=\""
			+ APP_URL
			+ "favicon.ico\" class=\"m-3\"><br _ngcontent-dtw-c54=\"\"><span _ngcontent-dtw-c54=\"\" class=\"h3 text-white\">ARM Captcha</span></div></div><div _ngcontent-dtw-c54=\"\" class=\"row bg-primary\"><img _ngcontent-dtw-c54=\"\" alt=\"ARM Captcha\" src=\""
			+ APP_URL
			+ "assets/cross-line.png\" width=\"100%\"></div><div _ngcontent-dtw-c54=\"\" class=\"row m-2 m-md-0 p-md-0\"><div _ngcontent-dtw-c54=\"\" class=\"col-12 m-4\"><h5 _ngcontent-dtw-c54=\"\" class=\"text-muted\">Inivitation</h5></div><div _ngcontent-dtw-c54=\"\" class=\"col-12 ml-4\"><p _ngcontent-dtw-c54=\"\" class=\"h2\">Earn upto ₱ 3000 per month</p></div><div _ngcontent-dtw-c54=\"\" class=\"col-12 ml-4\"><p _ngcontent-dtw-c54=\"\">Solve simple captchas and earn upto <b _ngcontent-dtw-c54=\"\">₱ 3000</b> per month</p></div><div _ngcontent-dtw-c54=\"\" class=\"col-12 m-4\"><a _ngcontent-dtw-c54=\"\" href=\""
			+ APP_URL
			+ "\" class=\"btn btn-primary btn-lg\">Register Now</a></div></div><div _ngcontent-dtw-c54=\"\" class=\"row bg-white\"><img _ngcontent-dtw-c54=\"\" alt=\"ARM Captcha\" src=\""
			+ APP_URL
			+ "assets/reverse-cross-line.png\" width=\"100%\"></div><div _ngcontent-dtw-c54=\"\" class=\"row m-2 m-md-0 p-md-0 justify-content-center text-black\"><h3 _ngcontent-dtw-c54=\"\">How to earn?</h3><p _ngcontent-dtw-c54=\"\" class=\"m-4\">Solve Captcha allotated to you and you can earn around ₱ 100 per day by solving 10 captchas. You can earn upto ₱ 10 for solving each captcha correctly. No deduction on wrong answer. </p><h4 _ngcontent-dtw-c54=\"\" class=\"mb-4 mb-md-0\"><b _ngcontent-dtw-c54=\"\">Happy Earning!!!</b></h4></div></div>\r\n"
			+ "</body>\r\n" + "</html>";

	public static final String EMAIL_VERIFICATION_TEMPLATE = HEAD
			+ "<body><div _ngcontent-trh-c54=\"\" class=\"container\"><div _ngcontent-trh-c54=\"\" class=\"row bg-primary justify-content-center\"><div _ngcontent-trh-c54=\"\" class=\"text-center\"><img _ngcontent-trh-c54=\"\" alt=\"ARM Captcha\" src=\"favicon.ico\" class=\"m-3\"><br _ngcontent-trh-c54=\"\"><span _ngcontent-trh-c54=\"\" class=\"h3 text-white\">ARM Captcha</span></div></div><div _ngcontent-trh-c54=\"\" class=\"row bg-primary\"><img _ngcontent-trh-c54=\"\" alt=\"ARM Captcha\" src=\"assets/cross-line.png\" width=\"100%\"></div><div _ngcontent-trh-c54=\"\" class=\"row m-2 m-md-0 p-md-0\"><div _ngcontent-trh-c54=\"\" class=\"col-12 m-4\"><h1 _ngcontent-trh-c54=\"\" class=\"text-primary\">Dear "
			+ NAME
			+ ",</h1></div><div _ngcontent-trh-c54=\"\" class=\"col-12 ml-4\"><p _ngcontent-trh-c54=\"\"><b _ngcontent-trh-c54=\"\">ARM Captcha</b> welcomes you on-board, we are glad you registered with us!</p><p _ngcontent-trh-c54=\"\">Here is your email verification link:</p><p _ngcontent-trh-c54=\"\"><a _ngcontent-trh-c54=\"\" href=\""
			+ VERIFY_EMAIL_URL
			+ "\">Verify your E-mail</a></p><p _ngcontent-trh-c54=\"\">Please do not share this link with anyone.</p></div></div><div _ngcontent-trh-c54=\"\" class=\"row bg-white\"><img _ngcontent-trh-c54=\"\" alt=\"ARM Captcha\" src=\"assets/reverse-cross-line.png\" width=\"100%\"></div><div _ngcontent-trh-c54=\"\" class=\"row m-2 m-md-0 p-md-0 justify-content-center text-black\"><div _ngcontent-trh-c54=\"\" class=\"col-12 m-4\"><h3 _ngcontent-trh-c54=\"\">Start earning now</h3></div><div _ngcontent-trh-c54=\"\" class=\"col-12 m-4\"><p _ngcontent-trh-c54=\"\" class=\"m-4\"><a _ngcontent-trh-c54=\"\" href=\""
			+ APP_URL
			+ "\" class=\"btn btn-primary btn-lg\">Login to earn</a></p></div><div _ngcontent-trh-c54=\"\" class=\"col-12 m-4\"><h4 _ngcontent-trh-c54=\"\" class=\"mb-4 mb-md-0\"><b _ngcontent-trh-c54=\"\">Happy Earning!!!</b></h4></div></div></div>"
			+ "</body>\r\n" + "</html>";

	public static final String WELCOME_TEMPLATE = HEAD
			+ "<div _ngcontent-hko-c54=\"\" class=\"container\"><div _ngcontent-hko-c54=\"\" class=\"row bg-primary justify-content-center\"><div _ngcontent-hko-c54=\"\" class=\"text-center\"><img _ngcontent-hko-c54=\"\" alt=\"ARM Captcha\" src=\"favicon.ico\" class=\"m-3\"><br _ngcontent-hko-c54=\"\"><span _ngcontent-hko-c54=\"\" class=\"h3 text-white\">ARM Captcha</span></div></div><div _ngcontent-hko-c54=\"\" class=\"row bg-primary\"><img _ngcontent-hko-c54=\"\" alt=\"ARM Captcha\" src=\"assets/cross-line.png\" width=\"100%\"></div><div _ngcontent-hko-c54=\"\" class=\"row m-2 m-md-0 p-md-0\"><div _ngcontent-hko-c54=\"\" class=\"col-12 m-4\"><h1 _ngcontent-hko-c54=\"\" class=\"text-primary\">Dear "
			+ NAME
			+ ",</h1></div><div _ngcontent-hko-c54=\"\" class=\"col-12 ml-4\"><p _ngcontent-hko-c54=\"\">Congratulation on successful registration with <b _ngcontent-hko-c54=\"\">ARM Captcha</b>!</p><p _ngcontent-hko-c54=\"\">As a welcome bonus, we have added <b>"
			+ CURRENCY + " " + AMOUNT
			+ "</b> in your wallet</p><p _ngcontent-hko-c54=\"\"><a _ngcontent-hko-c54=\"\" href=\"" + APP_URL
			+ "\">Login to check your wallet balance</a></p><p _ngcontent-hko-c54=\"\">Please do not share this link with anyone.</p></div></div><div _ngcontent-hko-c54=\"\" class=\"row bg-white\"><img _ngcontent-hko-c54=\"\" alt=\"ARM Captcha\" src=\"assets/reverse-cross-line.png\" width=\"100%\"></div><div _ngcontent-hko-c54=\"\" class=\"row m-2 m-md-0 p-md-0 justify-content-center text-black\"><div _ngcontent-hko-c54=\"\" class=\"col-12 m-4\"><h3 _ngcontent-hko-c54=\"\">Start earning now</h3></div><div _ngcontent-hko-c54=\"\" class=\"col-12 m-4\"><p _ngcontent-hko-c54=\"\" class=\"m-4\"><a _ngcontent-hko-c54=\"\" href=\""
			+ APP_URL
			+ "\" class=\"btn btn-primary btn-lg\">Login to earn</a></p></div><div _ngcontent-hko-c54=\"\" class=\"col-12 m-4\"><h4 _ngcontent-hko-c54=\"\" class=\"mb-4 mb-md-0\"><b _ngcontent-hko-c54=\"\">Happy Earning!!!</b></h4></div></div></div>"
			+ "</body>\r\n" + "</html>";
	public static final String NEW_CAPTCHA_TEMPLATE = HEAD
			+ "<div _ngcontent-vft-c54=\"\" class=\"container\"><div _ngcontent-vft-c54=\"\" class=\"row bg-primary justify-content-center\"><div _ngcontent-vft-c54=\"\" class=\"text-center\"><img _ngcontent-vft-c54=\"\" alt=\"ARM Captcha\" src=\"favicon.ico\" class=\"m-3\"><br _ngcontent-vft-c54=\"\"><span _ngcontent-vft-c54=\"\" class=\"h3 text-white\">ARM Captcha</span></div></div><div _ngcontent-vft-c54=\"\" class=\"row bg-primary\"><img _ngcontent-vft-c54=\"\" alt=\"ARM Captcha\" src=\"assets/cross-line.png\" width=\"100%\"></div><div _ngcontent-vft-c54=\"\" class=\"row m-2 m-md-0 p-md-0\"><div _ngcontent-vft-c54=\"\" class=\"col-12 m-4\"><h1 _ngcontent-vft-c54=\"\" class=\"text-primary\">Dear "
			+ NAME
			+ ",</h1></div><div _ngcontent-vft-c54=\"\" class=\"col-12 ml-4\"><p _ngcontent-vft-c54=\"\"><b _ngcontent-vft-c54=\"\">ARM Captcha</b> has added new Captchas in your account, solve them and earn money!</p><p _ngcontent-vft-c54=\"\">We have added daily "
			+ CAPTCHA_COUNT
			+ " Captchas in your account, please solve them and earn money</p><p _ngcontent-vft-c54=\"\"><a _ngcontent-vft-c54=\"\" href=\""
			+ APP_URL
			+ "\">Login to solve captcha</a></p><p _ngcontent-vft-c54=\"\">Please do not share this link with anyone.</p></div></div><div _ngcontent-vft-c54=\"\" class=\"row bg-white\"><img _ngcontent-vft-c54=\"\" alt=\"ARM Captcha\" src=\"assets/reverse-cross-line.png\" width=\"100%\"></div><div _ngcontent-vft-c54=\"\" class=\"row m-2 m-md-0 p-md-0 justify-content-center text-black\"><div _ngcontent-vft-c54=\"\" class=\"col-12 m-4\"><h3 _ngcontent-vft-c54=\"\">Start earning now</h3></div><div _ngcontent-vft-c54=\"\" class=\"col-12 m-4\"><p _ngcontent-vft-c54=\"\" class=\"m-4\"><a _ngcontent-vft-c54=\"\" href=\""
			+ APP_URL
			+ "\" class=\"btn btn-primary btn-lg\">Login to earn</a></p></div><div _ngcontent-vft-c54=\"\" class=\"col-12 m-4\"><h4 _ngcontent-vft-c54=\"\" class=\"mb-4 mb-md-0\"><b _ngcontent-vft-c54=\"\">Happy Earning!!!</b></h4></div></div></div>"
			+ "</body>\r\n" + "</html>";

	public static final String CAPTCHA_SOLVED_TEMPLATE = HEAD
			+ "<div _ngcontent-bpy-c54=\"\" class=\"container\"><div _ngcontent-bpy-c54=\"\" class=\"row bg-primary justify-content-center\"><div _ngcontent-bpy-c54=\"\" class=\"text-center\"><img _ngcontent-bpy-c54=\"\" alt=\"ARM Captcha\" src=\"favicon.ico\" class=\"m-3\"><br _ngcontent-bpy-c54=\"\"><span _ngcontent-bpy-c54=\"\" class=\"h3 text-white\">ARM Captcha</span></div></div><div _ngcontent-bpy-c54=\"\" class=\"row bg-primary\"><img _ngcontent-bpy-c54=\"\" alt=\"ARM Captcha\" src=\"assets/cross-line.png\" width=\"100%\"></div><div _ngcontent-bpy-c54=\"\" class=\"row m-2 m-md-0 p-md-0\"><div _ngcontent-bpy-c54=\"\" class=\"col-12 m-4\"><h1 _ngcontent-bpy-c54=\"\" class=\"text-primary\">Dear "
			+ NAME
			+ ",</h1></div><div _ngcontent-bpy-c54=\"\" class=\"col-12 ml-4\"><p _ngcontent-bpy-c54=\"\">Hurray!! You have solved captcha successful!!</p><p _ngcontent-bpy-c54=\"\">For successful captach solving we have added <b>"
			+ CURRENCY + " " + AMOUNT
			+ "</b> in your wallet</p><p _ngcontent-bpy-c54=\"\"><a _ngcontent-bpy-c54=\"\" href=\"" + APP_URL
			+ "\">Login to check your wallet balance</a></p><p _ngcontent-bpy-c54=\"\">Please do not share this link with anyone.</p></div></div><div _ngcontent-bpy-c54=\"\" class=\"row bg-white\"><img _ngcontent-bpy-c54=\"\" alt=\"ARM Captcha\" src=\"assets/reverse-cross-line.png\" width=\"100%\"></div><div _ngcontent-bpy-c54=\"\" class=\"row m-2 m-md-0 p-md-0 justify-content-center text-black\"><div _ngcontent-bpy-c54=\"\" class=\"col-12 m-4\"><h3 _ngcontent-bpy-c54=\"\">Start earning now</h3></div><div _ngcontent-bpy-c54=\"\" class=\"col-12 m-4\"><p _ngcontent-bpy-c54=\"\" class=\"m-4\"><a _ngcontent-bpy-c54=\"\" href=\""
			+ APP_URL
			+ "\" class=\"btn btn-primary btn-lg\">Login to earn</a></p></div><div _ngcontent-bpy-c54=\"\" class=\"col-12 m-4\"><h4 _ngcontent-bpy-c54=\"\" class=\"mb-4 mb-md-0\"><b _ngcontent-bpy-c54=\"\">Happy Earning!!!</b></h4></div></div></div>"
			+ "</body>\r\n" + "</html>";

	public static final String BIRTHDAY_TEMPLATE = HEAD
			+ "<div _ngcontent-avd-c54=\"\" class=\"container\"><div _ngcontent-avd-c54=\"\" class=\"row bg-primary justify-content-center\"><div _ngcontent-avd-c54=\"\" class=\"text-center\"><img _ngcontent-avd-c54=\"\" alt=\"ARM Captcha\" src=\"favicon.ico\" class=\"m-3\"><br _ngcontent-avd-c54=\"\"><span _ngcontent-avd-c54=\"\" class=\"h3 text-white\">ARM Captcha</span></div></div><div _ngcontent-avd-c54=\"\" class=\"row bg-primary\"><img _ngcontent-avd-c54=\"\" alt=\"ARM Captcha\" src=\"assets/cross-line.png\" width=\"100%\"></div><div _ngcontent-avd-c54=\"\" class=\"row m-2 m-md-0 p-md-0\"><div _ngcontent-avd-c54=\"\" class=\"col-12 m-4\"><h1 _ngcontent-avd-c54=\"\" class=\"text-primary\">Happy birthday "
			+ NAME
			+ " !!</h1></div><div _ngcontent-avd-c54=\"\" class=\"col-12 ml-4\"><p _ngcontent-avd-c54=\"\">As a token of appreciation, we have added <b>"
			+ CURRENCY + " " + AMOUNT
			+ "</b> in your wallet</p><p _ngcontent-avd-c54=\"\">May you be blessed with lots of prosperity and happiness! Love from ARM Captcha family!</p><p _ngcontent-avd-c54=\"\"><a _ngcontent-avd-c54=\"\" href=\""
			+ APP_URL
			+ "\">Login to check your wallet balance</a></p><p _ngcontent-avd-c54=\"\">Please do not share this link with anyone.</p></div></div><div _ngcontent-avd-c54=\"\" class=\"row bg-white\"><img _ngcontent-avd-c54=\"\" alt=\"ARM Captcha\" src=\"assets/reverse-cross-line.png\" width=\"100%\"></div><div _ngcontent-avd-c54=\"\" class=\"row m-2 m-md-0 p-md-0 justify-content-center text-black\"><div _ngcontent-avd-c54=\"\" class=\"col-12 m-4\"><h3 _ngcontent-avd-c54=\"\">Start earning now</h3></div><div _ngcontent-avd-c54=\"\" class=\"col-12 m-4\"><p _ngcontent-avd-c54=\"\" class=\"m-4\"><a _ngcontent-avd-c54=\"\" href=\""
			+ APP_URL
			+ "\" class=\"btn btn-primary btn-lg\">Login to earn</a></p></div><div _ngcontent-avd-c54=\"\" class=\"col-12 m-4\"><h4 _ngcontent-avd-c54=\"\" class=\"mb-4 mb-md-0\"><b _ngcontent-avd-c54=\"\">Happy Earning!!!</b></h4></div></div></div>"
			+ "</body>\r\n" + "</html>";

	public static final String CHRISTMAS_TEMPLATE = HEAD
			+ "<div _ngcontent-hoj-c54=\"\" class=\"container\"><div _ngcontent-hoj-c54=\"\" class=\"row bg-primary justify-content-center\"><div _ngcontent-hoj-c54=\"\" class=\"text-center\"><img _ngcontent-hoj-c54=\"\" alt=\"ARM Captcha\" src=\"favicon.ico\" class=\"m-3\"><br _ngcontent-hoj-c54=\"\"><span _ngcontent-hoj-c54=\"\" class=\"h3 text-white\">ARM Captcha</span></div></div><div _ngcontent-hoj-c54=\"\" class=\"row bg-primary\"><img _ngcontent-hoj-c54=\"\" alt=\"ARM Captcha\" src=\"assets/cross-line.png\" width=\"100%\"></div><div _ngcontent-hoj-c54=\"\" class=\"row m-2 m-md-0 p-md-0\"><div _ngcontent-hoj-c54=\"\" class=\"col-12 m-4\"><h1 _ngcontent-hoj-c54=\"\" class=\"text-primary\">Merry Christmas "
			+ NAME
			+ " !!</h1></div><div _ngcontent-hoj-c54=\"\" class=\"col-12 ml-4\"><p _ngcontent-hoj-c54=\"\">As a token of love, we have added <b>"
			+ CURRENCY + " " + AMOUNT
			+ "</b> in your wallet</p><p _ngcontent-hoj-c54=\"\">May Santa brings everything you wished for, Merry Christmas! Love from ARM Captcha family!</p><p _ngcontent-hoj-c54=\"\"><a _ngcontent-hoj-c54=\"\" href=\""
			+ APP_URL
			+ "\">Login to check your wallet balance</a></p><p _ngcontent-hoj-c54=\"\">Please do not share this link with anyone.</p></div></div><div _ngcontent-hoj-c54=\"\" class=\"row bg-white\"><img _ngcontent-hoj-c54=\"\" alt=\"ARM Captcha\" src=\"assets/reverse-cross-line.png\" width=\"100%\"></div><div _ngcontent-hoj-c54=\"\" class=\"row m-2 m-md-0 p-md-0 justify-content-center text-black\"><div _ngcontent-hoj-c54=\"\" class=\"col-12 m-4\"><h3 _ngcontent-hoj-c54=\"\">Start earning now</h3></div><div _ngcontent-hoj-c54=\"\" class=\"col-12 m-4\"><p _ngcontent-hoj-c54=\"\" class=\"m-4\"><a _ngcontent-hoj-c54=\"\" href=\""
			+ APP_URL
			+ "\" class=\"btn btn-primary btn-lg\">Login to earn</a></p></div><div _ngcontent-hoj-c54=\"\" class=\"col-12 m-4\"><h4 _ngcontent-hoj-c54=\"\" class=\"mb-4 mb-md-0\"><b _ngcontent-hoj-c54=\"\">Happy Earning!!!</b></h4></div></div></div>"
			+ "</body>\r\n" + "</html>";

	public static final String WITHDRAWAL_TEMPLATE = HEAD
			+ "<div _ngcontent-lcp-c54=\"\" class=\"container\"><div _ngcontent-lcp-c54=\"\" class=\"row bg-primary justify-content-center\"><div _ngcontent-lcp-c54=\"\" class=\"text-center\"><img _ngcontent-lcp-c54=\"\" alt=\"ARM Captcha\" src=\"favicon.ico\" class=\"m-3\"><br _ngcontent-lcp-c54=\"\"><span _ngcontent-lcp-c54=\"\" class=\"h3 text-white\">ARM Captcha</span></div></div><div _ngcontent-lcp-c54=\"\" class=\"row bg-primary\"><img _ngcontent-lcp-c54=\"\" alt=\"ARM Captcha\" src=\"assets/cross-line.png\" width=\"100%\"></div><div _ngcontent-lcp-c54=\"\" class=\"row m-2 m-md-0 p-md-0\"><div _ngcontent-lcp-c54=\"\" class=\"col-12 m-4\"><h1 _ngcontent-lcp-c54=\"\" class=\"text-primary\">Dear "
			+ NAME
			+ ",</h1></div><div _ngcontent-lcp-c54=\"\" class=\"col-12 ml-4\"><p _ngcontent-lcp-c54=\"\">We have received request for withdrawal of <b>"
			+ CURRENCY + " " + AMOUNT + "</b>.</p><p _ngcontent-lcp-c54=\"\">The reference number for the request is "
			+ TRAN_REF
			+ ". Please use this referene number for further communication.</p><p _ngcontent-lcp-c54=\"\">We will initiate the transaction on your PayPal address - "
			+ PAY_PAL_ADDRESS
			+ ". Please be informed transaction can take upto 3 working days for intiation. Please be patient, we will update you once we initiate the transaction!</p><p _ngcontent-lcp-c54=\"\"><a _ngcontent-lcp-c54=\"\" href=\""
			+ APP_URL
			+ "\">Login to check your wallet balance</a></p><p _ngcontent-lcp-c54=\"\">Please do not share this link with anyone.</p></div></div><div _ngcontent-lcp-c54=\"\" class=\"row bg-white\"><img _ngcontent-lcp-c54=\"\" alt=\"ARM Captcha\" src=\"assets/reverse-cross-line.png\" width=\"100%\"></div><div _ngcontent-lcp-c54=\"\" class=\"row m-2 m-md-0 p-md-0 justify-content-center text-black\"><div _ngcontent-lcp-c54=\"\" class=\"col-12 m-4\"><h3 _ngcontent-lcp-c54=\"\">Start earning now</h3></div><div _ngcontent-lcp-c54=\"\" class=\"col-12 m-4\"><p _ngcontent-lcp-c54=\"\" class=\"m-4\"><a _ngcontent-lcp-c54=\"\" href=\""
			+ APP_URL
			+ "\" class=\"btn btn-primary btn-lg\">Login to earn</a></p></div><div _ngcontent-lcp-c54=\"\" class=\"col-12 m-4\"><h4 _ngcontent-lcp-c54=\"\" class=\"mb-4 mb-md-0\"><b _ngcontent-lcp-c54=\"\">Happy Earning!!!</b></h4></div></div></div>"
			+ "</body>\r\n" + "</html>";
}
