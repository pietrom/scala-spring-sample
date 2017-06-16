import React from 'react'
import ReactDOM from 'react-dom'
import { HashRouter as Router, Route, Link } from 'react-router-dom'

import Primes from './Primes'
import Chat from './Chat'
import { Hello, HelloWorld, HelloPietro, HelloReact } from './Hello'

const App = React.createClass( {
    displayName: 'App',
    getInitialState: function() {
        return {
            login: 'pietrom',
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
                            <li><Link to="/primes">Prime factor decomposition</Link></li>
                            <li><Link to="/chat">Chat</Link></li>
                        </ul>

                        <hr />

                        <Route exact path="/" component={HelloWorld} />
                        <Route path="/pietro" component={HelloPietro} />
                        <Route path="/reactjs" component={HelloReact} />
                        <Route path="/primes" component={Primes} />
                        <Route path="/chat" component={Chat} />
                    </div>
                </Router>               
            </div>
        )
    }
} )

ReactDOM.render((
    <App />
), document.getElementById( 'app-root' ) )
