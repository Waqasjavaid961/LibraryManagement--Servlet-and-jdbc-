<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.*, java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Library Management | Login & Register</title>
    <style>
        /* Modern UI Design */
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: 'Poppins', sans-serif; }
        
        body {
            background: linear-gradient(rgba(0,0,0,0.7), rgba(0,0,0,0.7)), 
                        url('https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');
            background-size: cover;
            background-position: center;
            height: 100vh;
            display: flex;qas
            justify-content: center;
            align-items: center;
        }

        .container {
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(15px);
            padding: 40px;
            border-radius: 20px;
            border: 1px solid rgba(255, 255, 255, 0.2);
            box-shadow: 0 25px 50px rgba(0,0,0,0.3);
            width: 100%;
            max-width: 400px;
            color: white;
            transition: 0.5s;
        }

        h2 { text-align: center; margin-bottom: 30px; letter-spacing: 1.5px; font-weight: 600; }

        .input-group { margin-bottom: 20px; }
        
        .input-group input {
            width: 100%;
            padding: 12px 15px;
            background: rgba(255, 255, 255, 0.15);
            border: 1px solid rgba(255, 255, 255, 0.3);
            border-radius: 10px;
            outline: none;
            color: white;
            font-size: 15px;
            transition: 0.3s;
        }

        .input-group input:focus {
            border-color: #00d2ff;
            background: rgba(255, 255, 255, 0.25);
            box-shadow: 0 0 10px rgba(0, 210, 255, 0.3);
        }

        button {
            width: 100%;
            padding: 12px;
            border: none;
            border-radius: 10px;
            background: linear-gradient(45deg, #00d2ff, #3a7bd5);
            color: white;
            font-size: 17px;
            font-weight: 600;
            cursor: pointer;
            transition: 0.4s;
            margin-top: 10px;
            text-transform: uppercase;
        }

        button:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 20px rgba(0, 210, 255, 0.4);
        }

        .toggle-link { text-align: center; margin-top: 25px; font-size: 14px; }
        
        .toggle-link a {
            color: #00d2ff;
            text-decoration: none;
            font-weight: 600;
            cursor: pointer;
            border-bottom: 1px solid transparent;
            transition: 0.3s;
        }

        .toggle-link a:hover { border-bottom: 1px solid #00d2ff; }

        /* Error & Success Messages */
        .status-msg {
            padding: 12px;
            border-radius: 8px;
            text-align: center;
            margin-bottom: 20px;
            font-size: 14px;
        }
        .error { background: rgba(255, 71, 87, 0.3); border: 1px solid #ff4757; color: #ffbcbc; }
        .success { background: rgba(46, 213, 115, 0.3); border: 1px solid #2ed573; color: #b7ffce; }

        #register-form { display: none; }
    </style>
</head>
<body>

<div class="container">
    <div id="login-form">
        <h2>Login</h2>
        
        <% if(request.getParameter("error") != null) { %>
            <div class="status-msg error">❌ Invalid Credentials or Account Inactive.</div>
        <% } %>
        <% if("registered".equals(request.getParameter("msg"))) { %>
            <div class="status-msg success">✅ Registration Successful! Login now.</div>
        <% } %>
        <%-- index.jsp mein ye message logic behtar karo --%>
<% if("invalid".equals(request.getParameter("error"))) { %>
    <div class="status-msg error">❌ Galat Email ya Password! Dubara koshish karein.</div>
<% } %>

        <form action="LoginInput" method="POST">
            <div class="input-group">
                <input type="email" name="gmail" placeholder="Enter Gmail" required>
            </div>
            <div class="input-group">
                <input type="password" name="password" placeholder="Enter Password" required>
            </div>
            <button type="submit">Login</button>
        </form>
        <div class="toggle-link">
            Don't have an account? <a onclick="toggleForm('register')">Register</a>
        </div>
    </div>

    <div id="register-form">
        <h2>Register</h2>
        <form action="RegisterInput" method="POST">
            <div class="input-group">
                <input type="text" name="name" placeholder="Your Full Name" required>
            </div>
            <div class="input-group">
                <input type="email" name="gmail" placeholder="Gmail (example@gmail.com)" 
                       pattern="[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-z]{2,}$" required>
            </div>
            <div class="input-group">
                <input type="password" name="password" placeholder="Create Password" required>
            </div>
            <button type="submit" style="background: linear-gradient(45deg, #2ecc71, #27ae60);">Sign Up</button>
        </form>
        <div class="toggle-link">
            Already a member? <a onclick="toggleForm('login')">Back to Login</a>
        </div>
    </div>
</div>

<script>
    function toggleForm(type) {
        const login = document.getElementById('login-form');
        const register = document.getElementById('register-form');
        if(type === 'register') {
            login.style.opacity = '0';
            setTimeout(() => {
                login.style.display = 'none';
                register.style.display = 'block';
                register.style.opacity = '1';
            }, 300);
        } else {
            register.style.opacity = '0';
            setTimeout(() => {
                register.style.display = 'none';
                login.style.display = 'block';
                login.style.opacity = '1';
            }, 300);
        }
    }
</script>

</body>
</html>