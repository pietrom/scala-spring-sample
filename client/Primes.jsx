import React from 'react'
import EventSourceClient from './EventSourceClient'

const Primes = React.createClass({
    getInitialState: function() { return { number: '', primeFactors: [], completed: false, decomposing: '' } },
    numberChanged: function(event) {
        this.setState({ number: event.target.value })
    },
    decompose: function() {
        const client = EventSourceClient('/api/primes/' + this.state.number)
        this.setState({ number: '', primeFactors: [], completed: false, decomposing: this.state.number })
        client.onEvent((prime) => {
            const primeFactors = this.state.primeFactors.concat([ prime ])
            this.setState({ primeFactors: primeFactors })
        })
        client.onClose(() => {
            this.setState({ completed: true })
        })
    },
    render: function() {
        return <div>
            <input value={this.state.number} onChange={this.numberChanged} />
            <button onClick={this.decompose}>Decompose</button>
            <p>Decomposing { this.state.decomposing }</p>
            <ul>
                        {this.state.primeFactors.map((prime, i) => <li key={i}>{prime}</li>)}
            </ul>
            { this.state.completed ? '... ... ...' : 'Completed'}
        </div>
    }
})

module.exports = Primes