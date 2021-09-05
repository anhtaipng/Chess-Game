import React from 'react';
import './App.css';
import Header from "./features/header/Header";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import {HomePage} from "./features/homepage/HomePage";
import Game from "./features/chessgame/Game";
import {useSelector} from "react-redux";
import {UserConstant} from "./slices/user/userSlice";
import LoginForm from "./features/forms/LoginForm";
import RegisterForm from "./features/forms/RegisterForm";
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
            return <div>
                <Router>
                    <Switch>
                        <Route exact path="/" component={HomePage}>
                            {/* Render Home Element: Some beautiful main page */}
                            {/* <Home className="main-content"></Home> */}
                        </Route>
                        <Route exact path="/game/:game_id" component={Game}>
                            {/* Render major incoming updates from Better Messenger */}
                            {/* <Announcement className="main-content"></Announcement> */}
                        </Route>
                        {/*<Route exact path="/contacts/" component={Contacts}>*/}
                        {/*    /!* Render possible contacts to start new conversation *!/*/}
                        {/*    /!* <Contacts className="main-content"></Contacts> *!/*/}
                        {/*</Route>*/}
                        {/*<Route exact path="/info/:user_id" component={Info}>*/}
                        {/*    /!* Render the current login user info *!/*/}
                        {/*    /!* <UserInfo className="main-content"></UserInfo> *!/*/}
                        {/*</Route>*/}
                        {/*<Route exact path="/settings" component={Settings}>*/}
                        {/*    /!* Render the setting page for personalization *!/*/}
                        {/*    /!* <Settings className="main-content"></Settings> *!/*/}
                        {/*</Route>*/}
                    </Switch>
                </Router>
            </div>
    }
}

function App() {
    const logging_status = useSelector(state => state.user.login_status);
    return (
        <div className="App">
            <Header/>
            {renderSwitch(logging_status)}
        </div>
    );
}

export default App;
