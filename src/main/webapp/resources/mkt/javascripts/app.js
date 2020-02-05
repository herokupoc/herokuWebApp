

function printTable (time)
{
		opts = {simple: true};
        var table = {};
        var data  = time || {};
        console.log (data);
        Object.keys(data).sort().forEach(function(k) {
            table[k] = {
                ms: data[k],
                s: +((data[k] / 1000).toFixed(2))
            };
        });
        console.table(table);    
}

function sendTiming(time) {
	
    $.ajax({
        url : "http://localhost:5555/browserTiming",
        contentType: "application/json",
        type: 'POST',
        async: false,
        data: JSON.stringify(time),
        success: function (data) {
        	console.log ("Timing data has been sent properly");
        }
    });
   
}

function getCookie(cname) {
	  var name = cname + "=";
	  var decodedCookie = decodeURIComponent(document.cookie);
	  var ca = decodedCookie.split(';');
	  for(var i = 0; i <ca.length; i++) {
	    var c = ca[i];
	    while (c.charAt(0) == ' ') {
	      c = c.substring(1);
	    }
	    if (c.indexOf(name) == 0) {
	      return c.substring(name.length, c.length);
	    }
	  }
	  return "";
	}

function clear_resource_timings() {
	if (performance === undefined) {
		console.log("= performance.clearResourceTimings(): peformance NOT supported");
		return;
	}
	// Check if Performance.clearResourceTiming() is supported
	console.log ("= Print performance.clearResourceTimings()");
	var supported = typeof performance.clearResourceTimings == "function";
	if (supported) {
		console.log("... Performance.clearResourceTimings() = supported");
		performance.clearResourceTimings();
	} else {
		console.log("... Performance.clearResourceTiming() = NOT supported");
		return;
	}
	// getEntries should now return zero
	var p = performance.getEntriesByType("resource");
	if (p.length == 0)
		console.log("... Performance data buffer cleared");
	else
		console.log("... Performance data buffer NOT cleared (still have `" + p.length + "` items");
}

function calculate_load_times() {
	
	var api = {};
	
	
	// Check performance support
	if (performance === undefined) {
		console.log("= Calculate Load Times: performance NOT supported");
		return;
	}

	// Get a list of "resource" performance entries
	var resources = performance.getEntriesByType("resource");
	if (resources === undefined || resources.length <= 0) {
		console.log("= Calculate Load Times: there are NO 'resource' performance records");
		return;
	}

	console.log("= Calculate Load Times");
	for (var i=0; i < resources.length; i++) {
		console.log("El número de i es: " + i + " " + resources[i].initiatorType);
		if (resources[i].initiatorType == "xmlhttprequest")
  	   	{
			
			api = aggregateData(resources[i], api)
			api.pathnameUri = location.pathname;
			api.cause = "xmlhttprequest";
			api.searchUri = location.search;
			api.userAgent = navigator.userAgent;
			api.userName = getCookie ("userName");
			
			sendTiming (api);
			/*
			console.log("== Resource[" + i + "] - " + resources[i].name);
			// Redirect time
			var t = resources[i].redirectEnd - resources[i].redirectStart;
			console.log("... Redirect time = " + t);
	
			// DNS time
			t = resources[i].domainLookupEnd - resources[i].domainLookupStart;
			console.log("... DNS lookup time = " + t);
	
			// TCP handshake time
			t = resources[i].connectEnd - resources[i].connectStart;
			console.log("... TCP time = " + t);
	
			// Secure connection time
			t = (resources[i].secureConnectionStart > 0) ? (resources[i].connectEnd - resources[i].secureConnectionStart) : "0";
			console.log("... Secure connection time = " + t);
	
			// Response time
			t = resources[i].responseEnd - resources[i].responseStart;
			console.log("... Response time = " + t);
	
			// Fetch until response end
			t = (resources[i].fetchStart > 0) ? (resources[i].responseEnd - resources[i].fetchStart) : "0";
			console.log("... Fetch until response end time = " + t);
	
			// Request start until reponse end
			t = (resources[i].requestStart > 0) ? (resources[i].responseEnd - resources[i].requestStart) : "0";
			console.log("... Request start until response end time = " + t);
	
			// Start until reponse end
			t = (resources[i].startTime > 0) ? (resources[i].responseEnd - resources[i].startTime) : "0";
			console.log("... Start until response end time = " + t);
			*/
			//console.log("... All = " + JSON.stringify(resources[i]));
			console.log("... All = " + JSON.stringify(resources[i]));
			console.log (api.pathnameUri);
			console.log (api.searchUri);
			console.log (api.userAgent);
			console.log (api.userName);
  	   	}
	}
}
function htmlTimingPage() {
	
	console.log ("htmlTimingPage");
	var time = timing.getTimes();
	time.pathnameUri = location.pathname;
	time.searchUri = location.search;
	time.userAgent = navigator.userAgent;
	time.userName = getCookie ("userName");
	time.cause = "document";
	
	timing.printSimpleTable();
	console.log (time.pathnameUri);
	console.log (time.searchUri);
	console.log (time.userAgent);
	console.log (time.userName);
	
	sendTiming (time);
	
	
}

$(document).ready(function(){
	
	$.ajax({
		   beforeSend: function(){
			   // Handle the beforeSend event
			   //console.log ("beforeSend: ");
			   //window.performance.mark('mark_start_ajax_call');  
			   //printTable();
			   console.log ("Ajax Before send");
			   clear_resource_timings();
			   
			   
			   
		   },
		   complete: function(){
			   
			   
			   
			   // Handle the complete event
			   /*
			   console.log ("complete");
			   window.performance.mark('mark_end_ajax_call');
			   window.performance.measure('measure_ajax_call', 'mark_start_ajax_call', 'mark_end_ajax_call');
			   
			   console.log(window.performance.getEntriesByType("measure"));

			   
			   performance.clearMarks();
			   performance.clearMeasures();
			   
			   var resourceList = window.performance.getEntriesByType("resource");
	           for (i = 0; i < resourceList.length; i++)
	           {
	        	   if (resourceList[i].initiatorType == "xmlhttprequest")
	        	   {
		        	    console.log("New resource: ");
		                console.log("End to end resource initiatorType: "+ resourceList[i].initiatorType );
		                console.log("End to end resource name: "+ resourceList[i].name );
		                console.log("End to end resource server time: "+ resourceList[i].serverTiming );
		                var time = resourceList[i].responseEnd - resourceList[i].requestStart ;
		                console.log("Request start to response end: " + time );
	        	   }
	                
	           }
	           */
			   calculate_load_times();
			   console.log ("Ajax Complete");
			   
			  
		   }
	});
	 
	
});




