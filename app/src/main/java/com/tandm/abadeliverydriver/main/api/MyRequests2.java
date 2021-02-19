package com.tandm.abadeliverydriver.main.api;

import com.tandm.abadeliverydriver.main.zalo.TemplateDataParent;
import com.tandm.abadeliverydriver.main.zalo.TemplateDataParent2;
import com.tandm.abadeliverydriver.main.zalo.Zalo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyRequests2 {

//    @FormUrlEncoded
    @POST("message/template?access_token=IA1O64hXG5D8hL8QJkXDKqokK19IYoDSKv1FVIscFND4bqLeCOSWVZMzKsTzfIOe5BLuFLMEOHCzaomqPR9D2sUM7K4oYrvFLyv3KJBUBrr0s3Tf" +
            "6PrMOdsD57OQgqf2REvDTmhkNrSBo3vEKD9HTrJH5LiFuWXwPUTzPZFG3qPhx6e90lis75wvIm47XZC8KOLTDp64EZrFWpqL88DZAaA2A3uxXJ0YFRfTT" +
            "sQ7944GkaW86Pe45d69RpfoadSQBvCjBtEITJicrZqYTPP0B3oXEY1rxNuFC7KgU4lxH5a")
    Call<Zalo> SendZalo(
                        @Body TemplateDataParent templateDataParent
                        );


    @POST("message/template?access_token=IA1O64hXG5D8hL8QJkXDKqokK19IYoDSKv1FVIscFND4bqLeCOSWVZMzKsTzfIOe5BLuFLMEOHCzaomqPR9D2sUM7K4oYrvFLyv3KJBUBrr0s3Tf6PrMOdsD" +
            "57OQgqf2REvDTmhkNrSBo3vEKD9HTrJH5LiFuWXwPUTzPZFG3qPhx6e90lis75wvIm47XZC8KOLTDp64EZrFWpqL88DZAaA2A3uxXJ0YFRfTTsQ7944GkaW" +
            "86Pe45d69RpfoadSQBvCjBtEITJicrZqYTPP0B3oXEY1rxNuFC7KgU4lxH5a")
    Call<Zalo> SendZaloRating(
            @Body TemplateDataParent2 templateDataParent
    );

}
