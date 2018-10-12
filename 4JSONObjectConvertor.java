package cxf.client;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;

import com.bny.analytics.mlengine.objs.RRequest;

public class JSONObjectConvertor {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		// String jsonInput ="[{\"Option\":1,\"Question\":\"Lorem ipsum dolor
		// sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget
		// dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis
		// parturient montes, nascetur ridiculus mus. Donec quam felis,
		// ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat
		// massa quis enim. Donec pede justo, fringilla vel, aliquet nec,
		// vulputate eget, arcu.?\",\"Answer\":\"In enim justo, rhoncus ut,
		// imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede
		// mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum
		// semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula,
		// porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante,
		// dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla
		// ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam
		// ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam
		// eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum
		// rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed
		// ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id,
		// lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien
		// ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci
		// eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet
		// nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum
		// sodales, augue velit cursus nunc.\"},{\"Option\":2,\"Question\":\"How
		// are you test question2 dsf sdfsdf sdfsdf werwer erwerwer dsfdsf
		// sdfsada sadsad werewr waewqeqw weqwewq?\",\"Answer\":\"Yes, depending
		// on the information being made available to the market prior to
		// ex-date.\"},{\"Option\":3,\"Question\":\"How are you test
		// question3?\",\"Answer\":\"S.W.I.F.T., Workbench and
		// Fax.\"},{\"Option\":4,\"Question\":\"How are you test
		// question4?\",\"Answer\":\"nkjankdjas\"},{\"Option\":5,\"Question\":\"How
		// are you test question5?\",\"Answer\":\"YES\"}]";

		// This doesn't work :jackson parser exception
		// String jsonInput ="{\"Question\":\"Lorem ipsum dolor sit amet,
		// consectetuer adipiscing elit. Aenean commodo ligula eget dolor.
		// Aenean massa. Cum sociis natoque penatibus et magnis dis parturient
		// montes, nascetur ridiculus mus. Donec quam felis, ultricies nec,
		// pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim.
		// Donec pede justo, fringilla vel, aliquet nec, vulputate eget,
		// arcu.?\",\"Answer\":\"In enim justo, rhoncus ut, imperdiet a,
		// venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium.
		// Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi.
		// Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu,
		// consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in,
		// viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus
		// varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies
		// nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui.
		// Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem
		// quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam
		// nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec
		// odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis
		// faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus
		// tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec
		// sodales sagittis magna. Sed consequat, leo eget bibendum sodales,
		// augue velit cursus nunc.\"}";

		// making attribute names to lower case ,
		String jsonInput = "{\"question\":\"Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu.?\",\"answer\":\"In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc.\"}";

		ObjectMapper mapper = new ObjectMapper();
		
		// Response response = mapper.readValue(jsonInput, Response.class);
		// System.out.println(response);
		// System.out.println(response.getQuestion());
		// System.out.println(response.getAnswer());
		// System.out.println("nice");
		//

		// Simple example
		RRequest request = new RRequest();
		request.setCmd("library(datasets);output <- toupper(input)");
		request.setInputAsJson("India");
		String jsonInString = mapper.writeValueAsString(request);
		System.out.println(jsonInString);

		// String input="{\"answer\":\"Urvi\",\"question\":\"what is your
		// name\"}";
		// response = mapper.readValue(input, Response.class);
		// System.out.println(response);
		//
		// System.out.println(response.getQuestion());
		// System.out.println(response.getAnswer());

	}
}
