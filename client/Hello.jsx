import React from 'react'

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

module.exports = { Hello, HelloWorld, HelloPietro, HelloReact }