from flask import Flask, request, render_template, flash

app = Flask(__name__)

@app.route("/", methods=['POST','GET'])
def handle_data():
    try:
        if (request.method == 'POST'):
            #jsonify(data)
            return "Hello World - you sent me a POST " + str(request.values)
        else:
            return "Hello World - you sent me a GET " + str(request.values)
    except Exception as e:
        flash(e)
        return "Error" + str(e)



@app.route("/home", methods=['GET'])
def homepage():
    return render_template("index.html")

if __name__ == '__main__':
    app.run(host= '0.0.0.0')
