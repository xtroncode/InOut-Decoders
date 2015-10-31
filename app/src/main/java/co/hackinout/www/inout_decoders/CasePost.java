package co.hackinout.www.inout_decoders;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Created by meet on 31/10/15.
 */

//Data model for a new Medical Case
@ParseClassName("Case")
public class CasePost extends ParseObject{


    public static ParseQuery<CasePost> getQuery() {
        return ParseQuery.getQuery(CasePost.class);
    }
}
