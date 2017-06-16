import React from 'react'
import ReactDOM from 'react-dom'
import { HashRouter as Router, Route, Link } from 'react-router-dom'
import Websocket from 'react-websocket'

const Hello = function( props ) {
    return <p>Hello, {props.to || 'World'}!</p>
}

const HelloWorld = function( props ) {
    return <Hello />
}

const HelloPietro = function( props ) {
    return <Hello to="Pietro" />
}

const HelloReact = function( props ) {
    return <Hello to="ReactJs" />
}

const startClient = function() {
    const socket = new WebSocket( 'ws://localhost:2205/api/chat' )
    socket.addEventListener( 'open', function( event ) {
        console.log( 'WebSocket open' )
        socket.send( 'Hello Server!' )
    } )

    // Listen for messages
    socket.addEventListener( 'message', function(event) {
        console.log( 'Message from server', event.data )
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

const App = React.createClass( {
    displayName: 'App',
    getInitialState: function() {
        return {
            login: 'pietrom',
            messages: [],
            myMessage: ''
        }
    },
    componentWillMount: function() {
        this.client = startClient()
        this.client.onMessage((msg) => {
            console.log('Incoming message...', msg)
            const messages = this.state.messages.concat( [msg] )
            this.setState( { messages: messages } )
        })
    },
    myMessageChanged: function(event) {
        this.setState({ myMessage: event.target.value })
    },
    send: function() {
        if(this.state.myMessage) {
            this.client.send(this.state.myMessage)
            this.setState({ myMessage: '' })
        }
        
    },
    render: function() {
        return (
            <div>
                App
			<HelloWorld />
                <HelloPietro />
                <HelloReact />
                <strong><Hello to={this.state.login} /></strong>
                <Router>
                    <div>
                        <ul>
                            <li><Link to="/">Home</Link></li>
                            <li><Link to="/pietro">Pietro</Link></li>
                            <li><Link to="/reactjs">ReactJs</Link></li>
                        </ul>

                        <hr />

                        <Route exact path="/" component={HelloWorld} />
                        <Route path="/pietro" component={HelloPietro} />
                        <Route path="/reactjs" component={HelloReact} />
                    </div>
                </Router>
                <input value={this.state.myMessage} onChange={this.myMessageChanged} />
                <button onClick={this.send}>Send</button>
                <ul>
                            {this.state.messages.map((msg, i) => <li key={i}>{msg}</li>)}
                </ul>
            </div>
        )
    }
} )

ReactDOM.render((
    <App />
), document.getElementById( 'app-root' ) )
