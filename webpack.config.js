module.exports = {
	entry: {
		"app": "./client/App.jsx"
	},
	output: {
		path: "docroot/dist",
		filename: "[name].js",
	    publicPath: "/dist/"
	},
	module: {
	    noParse: [/html2canvas/],
		loaders: [{
			test: /\.jsx?$/,
			exclude: /node_modules/,
			loader: 'babel-loader',
			query: {
				presets: ['react', 'es2015']
			}
		}, {
			test: /\.css$/,
			loader: "style-loader!css-loader"
		}, {
		    test: /\.scss$/,
		    exclude: /node_modules/,
	        loaders: ["style-loader", "css-loader", "sass-loader"]
		},
        { test: /\.woff(2)?(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: "url-loader?limit=10000&mimetype=application/font-woff" },
      { test: /\.ttf(\?v=\d+\.\d+\.\d+)?$/, loader: "url?limit=10000&mimetype=application/octet-stream" },
      { test: /\.eot(\?v=[0-9]\.[0-9]\.[0-9])?$/, loader: "file" },
      { test: /\.svg(\?v=\d+\.\d+\.\d+)?$/, loader: "url?limit=10000&mimetype=image/svg+xml" },
      { test: /\.(png|jpg|jpeg|gif|woff)$/, loader: "url?limit=10000" }
	    ]
	},
	resolve: {
		extensions: ['', '.js', '.es6', '.jsx']
	}
}