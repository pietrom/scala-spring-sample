module.exports = function EventSourceClient(uri) {
	var evtSource = new EventSource(uri, {
		withCredentials : false
	});

	evtSource.onopen = function(e) {
		console.log("Connection to server opened.", e);
	};

	evtSource.onmessage = function(e) {
		console.log('RECEIVED', e);		
	};

	evtSource.onerror = function(e) {
		console.log('ERROR', e, e.target.readyState)
		evtSource.close();
	};
	
	const client = {
		onEvent : function(handler) {
			evtSource.onmessage = function(e) {
				console.log('RECEIVED', e)
				handler(e.data)
			}
		},
		onClose: function(handler) {
			evtSource.onclose= function(e) {				
				handler()
			}
		}
	}
	
	return client
}