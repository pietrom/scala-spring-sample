import React from 'react'

import WebSocketClient from './WebSocketClient'

const Chat = React.createClass({
    getInitialState: function() {
        return {
            messages: [],
            myMessage: ''
        }
    },
    componentWillMount: function() {
        this.client = WebSocketClient('ws://localhost:2205/api/chat')
        this.client.onMessage((msg) => {
            const messages = this.state.messages.concat([msg])
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
        return <div>
            <input value={this.state.myMessage} onChange={this.myMessageChanged} />
            <button onClick={this.send}>Send</button>
            <ul>
                        {this.state.messages.map((msg, i) => <li key={i}>{msg}</li>)}
            </ul>
        </div>
    }
})

module.exports = Chat