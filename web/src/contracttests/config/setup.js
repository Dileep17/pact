const path = require('path');
const Pact = require('@pact-foundation/pact').Pact;

global.port = 8080;
// global.provider = new Pact({
//     cors: true,
//     port: global.port,
//     log: path.resolve(process.cwd(), 'logs', 'pact.log'),
//     loglevel: 'debug',
//     dir: path.resolve(process.cwd(), 'pacts'),
//     spec: 2,
//     pactfileWriteMode: 'update',
//     consumer: 'web',
//     provider: 'productservice',
//     host: '127.0.0.1'
// });

global.provider= new Pact({
    port:8082,
    consumer: 'web',
    provider: 'productservice',
    log: path.resolve(process.cwd(), 'logs', 'pact.log'),
    logLevel: "DEBUG",
    dir: path.resolve(process.cwd(), 'pacts'),
    spec: 2,
    pactfileWriteMode: 'update'
});