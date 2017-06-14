import React from 'react'
import ReactDOM from 'react-dom'
import { Router, hashHistory } from 'react-router'

const Hello = function(props) {
    return <p>Hello, { props.to || 'World' }!</p>
}

const App = React.createClass ({
	displayName: 'App',
	getInitialState: function() {
		return {
		}
	},
	
  	render: function() {  		
    	return (
		<div>
			App
			<Hello />
			<Hello to="Pietro" />
		</div>
    )
  }
})

ReactDOM.render((
	<App />
),document.getElementById('app-root'))
