import React from 'react';
import './App.css';
import Header from "./features/header/Header";
import {BrowserRouter, BrowserRouter as Router, Route, Switch} from "react-router-dom";
import {HomePage} from "./features/homepage/HomePage";
import GameBoard from "./features/chessgame/GameBoard";
import {useSelector} from "react-redux";
import {UserConstant} from "./slices/user/userSlice";
import LoginForm from "./features/forms/LoginForm";
import RegisterForm from "./features/forms/RegisterForm";
import {Counter} from "./features/counter/Counter";


// ROOT SERVER USED WITH PROXY TO AUTHENTICATE FOR TESTING
const TEST_SERVER_ROOT = "";
export {TEST_SERVER_ROOT}

const renderSwitch = (login_status)=> {
    switch (login_status){
        case UserConstant.LOGGING_IN || UserConstant.LOGGING_IN_LOADING ||UserConstant.LOGGED_IN_FAILED:
            return <LoginForm/>
        case UserConstant.REGISTERING:
            return <RegisterForm/>
        case UserConstant.REGISTERING_LOADING:
            return <RegisterForm/>
        case UserConstant.REGISTERING_FAILED:
            return <RegisterForm/>
        case UserConstant.LOGGED_IN:
            return <HomePage/>
        default:
            console.log(login_status);
            return <Switch>
                        <Route exact path="/" component={HomePage}>
                            {/* Render Home Element: Some beautiful main page */}
                            {/* <Home className="main-content"></Home> */}
                        </Route>
                        <Route exact path="/game/:game_id" component={GameBoard}>
                        </Route>
                        <Route exact path="/testcomponent/" component={GameBoard}/>
                        <Route exact path="/counter" component={Counter}/>
                        {/*<Route exact path="/info/:user_id" component={Info}>*/}
                        {/*    /!* Render the current login user info *!/*/}
                        {/*    /!* <UserInfo className="main-content"></UserInfo> *!/*/}
                        {/*</Route>*/}
                        {/*<Route exact path="/settings" component={Settings}>*/}
                        {/*    /!* Render the setting page for personalization *!/*/}
                        {/*    /!* <Settings className="main-content"></Settings> *!/*/}
                        {/*</Route>*/}
                    </Switch>
    }
}

function App() {
    const logging_status = useSelector(state => state.user.login_status);
    return (
        <div className="App">
            <BrowserRouter>
                  <Header/>
                  {renderSwitch(logging_status)}
            </BrowserRouter>

        </div>
    );
}

export default App;
