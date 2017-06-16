module.exports = function(uri) {
    const socket = new WebSocket(uri)
    socket.addEventListener( 'open', function( event ) {
        console.log( 'WebSocket open' )
        socket.send( 'Hello Server!' )
    } )

    const client = {
        send: function( text ) {
            socket.send( text )
        },
        onMessage: function(handler) {
            socket.addEventListener('message', function(event) {
                handler(event.data)
            })
        }
    }
    return client
}