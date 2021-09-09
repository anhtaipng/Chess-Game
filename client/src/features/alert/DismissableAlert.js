import Alert from 'react-bootstrap/Alert'
import {useState} from "react";

function DismissibleAlert({heading,message,type}) {
    const [show, setShow] = useState(true);

    return (
        <>
            <Alert show={show} variant={type}>
                <Alert.Heading>{heading}</Alert.Heading>
                <p>
                    {message}
                </p>
                <hr />
                <div className="d-flex justify-content-end">
                    <button onClick={() => setShow(false)}>
                        Something is wrong. Try again later.
                    </button>
                </div>
            </Alert>
        </>
    );
}

export default DismissibleAlert;