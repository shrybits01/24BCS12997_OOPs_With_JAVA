<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Todo App</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', sans-serif;
            background: linear-gradient(135deg, #667eea, #764ba2);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .card {
            background: white;
            padding: 50px 60px;
            border-radius: 20px;
            text-align: center;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
        }
        h1 { font-size: 42px; color: #333; margin-bottom: 10px; }
        p { color: #888; margin-bottom: 30px; font-size: 16px; }
        a {
            display: inline-block;
            padding: 14px 36px;
            background: linear-gradient(135deg, #667eea, #764ba2);
            color: white;
            text-decoration: none;
            border-radius: 50px;
            font-size: 18px;
            font-weight: bold;
            transition: transform 0.2s, box-shadow 0.2s;
            box-shadow: 0 4px 15px rgba(102,126,234,0.5);
        }
        a:hover { transform: translateY(-2px); box-shadow: 0 8px 25px rgba(102,126,234,0.6); }
    </style>
</head>
<body>
    <div class="card">
        <h1>&#128221; Todo App</h1>
        <p>Stay organized. Get things done.</p>
        <a href="todo">Open My Todo List &#8594;</a>
    </div>
</body>
</html>