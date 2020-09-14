package com.xm.commerce.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

public class JsoupTest {

	@Test
	public void test() {
		String s = "<div class=\"detailmodule_html\"><div class=\"detail-desc-decorate-richtext\"><div><div><img src=\"https://ae01.alicdn.com/kf/H26fc1d1818c947e2bac8244f95e6cb5eG.png\"><div style=\"margin-left:1.6701461377871%;margin-top:-35.699373695198%;width:22.755741127349%;\"><a href=\"https://www.aliexpress.com/item/detail/4000055873024.html\" target=\"_blank\"><img src=\"https://ae01.alicdn.com/kf/Hbfa596eae53042cb8a6419f13cd44dcb9.png\"></a></div><div style=\"margin-left:26.200417536534%;margin-top:-35.490605427975%;width:22.755741127349%;\"><a href=\"https://www.aliexpress.com/item/detail/4000296264232.html\" target=\"_blank\"><img src=\"https://ae01.alicdn.com/kf/Hbfa596eae53042cb8a6419f13cd44dcb9.png\"></a></div><div style=\"margin-left:50.730688935282%;margin-top:-35.490605427975%;width:22.755741127349%;\"><a href=\"https://www.aliexpress.com/item/detail/32974989918.html\" target=\"_blank\"><img src=\"https://ae01.alicdn.com/kf/Hbfa596eae53042cb8a6419f13cd44dcb9.png\"></a></div><div style=\"margin-left:75.260960334029%;margin-top:-35.490605427975%;width:22.755741127349%;\"><a href=\"https://www.aliexpress.com/item/detail/4000232997532.html\" target=\"_blank\"><img src=\"https://ae01.alicdn.com/kf/Hbfa596eae53042cb8a6419f13cd44dcb9.png\"></a></div><div style=\"margin-top:0.20876826722338%;height:0;width:0;\"></div></div></div><div style=\"border:0;\"></div></div></div>\n" +
				"<div class=\"detailmodule_image\"><a href=\"https://www.aliexpress.com/item/4000106511626.html?spm=2114.12010615.8148356.9.13772dc7VQ7lHD\"><img src=\"https://ae01.alicdn.com/kf/H4827537f7b9746fca33010b8ee0a862dt.jpg\"  class=\"detail-desc-decorate-image\" ></a></div>\n" +
				"<div class=\"detailmodule_image\"><a href=\"https://www.aliexpress.com/item/32974989918.html?spm=2114.12010615.8148356.3.a4ef2dc7Y18a1h\"><img src=\"https://ae01.alicdn.com/kf/H54c73d92bd124b958bbd62eab96f7f75h.jpg\"  class=\"detail-desc-decorate-image\" ></a></div>\n" +
				"<div class=\"detailmodule_text\"><p class=\"detail-desc-decorate-content\" style=\"text-overflow: ellipsis;font-family: 'OpenSans';color:'#000';word-wrap: break-word;white-space: pre-wrap;font-weight: 300;font-size: 14px;line-height: 20px;color: #000;margin-bottom: 12px;\">   </p></div>\n" +
				"<div class=\"detailmodule_text\"><p class=\"detail-desc-decorate-content\" style=\"text-overflow: ellipsis;font-family: 'OpenSans';color:'#000';word-wrap: break-word;white-space: pre-wrap;font-weight: 300;font-size: 14px;line-height: 20px;color: #000;margin-bottom: 12px;\">   </p></div>\n" +
				"<div class=\"detailmodule_text\"><p class=\"detail-desc-decorate-content\" style=\"text-overflow: ellipsis;font-family: 'OpenSans';color:'#000';word-wrap: break-word;white-space: pre-wrap;font-weight: 300;font-size: 14px;line-height: 20px;color: #000;margin-bottom: 12px;\">   </p></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H276dc2a0e01a43d4a80b6116f8ca12502.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H1dec5d4080a0413ba67c3649aba04ca6A.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H33e49317f4524a64a68c7ef9a71d3ecdl.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H99dac3adba2c4438ba14ff9b4bcf200e6.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H9495b2b78454433995c60eea6c2107d0i.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/Heec4c4a5a2f944828de35a92f36d87b1G.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H8948b99e2106466fa31fd4644411b25ch.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H33512f807ae94899960ed3a254e63619r.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H083e46a671ef48feadf60ac68ef6abafg.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H58c4f68b2b36481583d3c560d9a8ad30t.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H912e197b81e0446b96d3891240d2ba9eQ.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H28ef657c9c8b410c9da2b136d7d7a84dF.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/He207f5edcafb45a7a1385eb8ec41c0c6e.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H362afdfe6df545029cb2a42470fa1296E.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H4a5d5da3153a4649b7f303349e3624b6N.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/Hf901bef931654a74a48c3ed4c0beabe4a.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/Hcec9dc0159354ded965d51575cb2f1a4U.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"<div class=\"detailmodule_image\"><img src=\"https://ae01.alicdn.com/kf/H469b2438a308468a802f384965a5a859Z.jpg\"  class=\"detail-desc-decorate-image\" ></div>\n" +
				"\n" +
				"<script>window.adminAccountId=222214133;</script>\n";
		Document document = Jsoup.parse(s);
		Elements images = document.select("img");
		for (Element img : images) {
			System.out.println(img.attr("src"));
			img.attr("src", "test");
		}
		Elements a = document.select("a");
		a.remove();
		System.out.println(document.body().toString());
	}
}
