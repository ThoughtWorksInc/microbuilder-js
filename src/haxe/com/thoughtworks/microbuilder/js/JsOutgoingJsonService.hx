package com.thoughtworks.microbuilder.js;

import com.thoughtworks.microbuilder.core.IRouteConfiguration;
import com.thoughtworks.microbuilder.core.CoreSerializer;
import com.thoughtworks.microbuilder.core.MicrobuilderOutgoingJsonService;
import com.thoughtworks.microbuilder.core.Failure;
import haxe.ds.Vector;

private typedef RequestOption = {
  var url(default, null):String;
  var method(default, null):String;
  var headers(default, null):Array<{name:String, value:String}>;
  var body(default, null):String;
};

private typedef Response = {
  var statusCode(default, null):Int;
}

private typedef RequestError = Null<Dynamic>;

private typedef ResponseBody = String;

private typedef RequestCallback = RequestError->Response->ResponseBody->Void;

@:expose
class JsOutgoingJsonService extends MicrobuilderOutgoingJsonService {

  var request:RequestOption->?RequestCallback->Void;

  public function new(urlPrefix: String, routeConfiguration:IRouteConfiguration, request) {
    super(urlPrefix, routeConfiguration);
    this.request = request;
  }

  override public function send(url:String, httpMethod:String, requestBody: String, headers:Vector<Header>, ?responseHandler:Null<Dynamic>->?Int->?String->Void):Void {
    var requestHeaders = [ for (header in headers) if (header.value != null) {
      name: header.name,
      value: header.value,
    }];
    if (responseHandler == null) {
      request(
        {
          url: url,
          method: httpMethod,
          headers: requestHeaders,
          body: requestBody,
        }
      );
    } else {
      request(
        {
          url: url,
          method: httpMethod,
          headers: requestHeaders,
          body: requestBody,
        },
        function (error, response, responseBody) {
          if (error == null) {
            responseHandler(null, response.statusCode, responseBody);
          } else {
            responseHandler(error);
          }
        }
      );
    }
  }


}
