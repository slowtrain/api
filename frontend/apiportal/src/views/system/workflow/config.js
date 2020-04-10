// eslint-disable-next-line
/* eslint-disable */
let interval = [1,9,0.25,9.75,0.25,9.75,0.25,9.75,0.25,9.75,0.25,9.75,0.25,9.75,0.25,9.75,0.25,9.75,0.25,9.75];

let rightPort = { id: 'rightPort', shape: 'Circle', offset: { x: 1, y: 0.5 } }
let leftPort = { id: 'leftPort', shape: 'Circle', offset: { x: 0, y: 0.5 } }

let gridlines = {
     lineColor: "#e0e0e0",
     lineIntervals: []
    // lineIntervals: interval
}

let nodes = [
  {
    id: "Start",
    offsetX: 150,
    offsetY: 200,
    shape: { type: "Flow", shape: "Terminator" },
    constraints: 5240814 & 2 & 4,
    style: { fill: '#65B091' },
    annotations: [{content: "Start"}],
    ports:[rightPort]
  },
  {
    id: "End",
    offsetX: 650,
    offsetY: 200,
    shape: { type: "Flow", shape: "Terminator" },
    constraints: ~64 & ~8 & ~1044480,
    style: { fill: '#D5535D' },
    annotations: [{content: "End"}],
    ports:[leftPort]
  },
 
];
//Initializes the connector for the diagram
let connectors = [
];

/*
let nodes = [
    {
      id: "Start",
      offsetX: 400,
      offsetY: 80,
      shape: { type: "Flow", shape: "Terminator" },
      constraints: ~64,
      annotations: [
        {
          content: "Start"
        }
      ]
    },
    {
      id: "Meeting",
      offsetX: 400,
      offsetY: 160,
      shape: { type: "Flow", shape: "Process" },
      annotations: [
        {
          content: "Start Transaction"
        }
      ]
    },
    {
      id: "BoardDecision",
      offsetX: 400,
      offsetY: 240,
      shape: { type: "Flow", shape: "Process" },
      annotations: [
        {
          content: "Verification"
        }
      ]
    },
    {
      id: "Project",
      offsetX: 400,
      offsetY: 330,
      shape: { type: "Flow", shape: "Decision" },
      annotations: [
        {
          content: "Credit card valid?"
        }
      ]
    },
    {
      id: "End",
      offsetX: 400,
      offsetY: 430,
      shape: { type: "Flow", shape: "Decision" },
      annotations: [
        {
          content: "Funds available?"
        }
      ]
    },
    {
      id: "node11",
      offsetX: 545,
      offsetY: 330,
      shape: { type: "Flow", shape: "Process" },
      annotations: [
        {
          content: "Enter payment method"
        }
      ]
    },
    {
      id: "transaction_entered",
      offsetX: 400,
      offsetY: 630,
      shape: { type: "Flow", shape: "Terminator" },
      annotations: [
        {
          content: "Log transaction"
        }
      ]
    },
    {
      id: "node12",
      offsetX: 480,
      offsetY: 630,
      shape: { type: "Flow", shape: "Process" },
      annotations: [
        {
          content: "Reconcile the entries"
        }
      ]
    },
    {
      id: "transaction_completed",
      offsetX: 400,
      offsetY: 530,
      shape: { type: "Flow", shape: "Process" },
      annotations: [
        {
          content: "Complete Transaction"
        }
      ]
    },
    {
      id: "Data",
      offsetX: 110,
      offsetY: 530,
      shape: { type: "Flow", shape: "Data" },
      annotations: [
        {
          content: "Send e-mail",
          margin: { left: 25, right: 25 }
        }
      ]
    },
    {
      id: "node10",
      offsetX: 475,
      offsetY: 530,
      shape: { type: "Flow", shape: "DirectData" },
      annotations: [
        { content: "Customer Database", margin: { left: 25, right: 25 } }
      ]
    }
  ];
  //Initializes the connector for the diagram
  let connectors = [
    {
      id: "connector1",
      sourceID: "Start",
      targetID: "Meeting"
    },
    { id: "connector2", sourceID: "Meeting", targetID: "BoardDecision" },
    { id: "connector3", sourceID: "BoardDecision", targetID: "Project" },
    {
      id: "connector4",
      sourceID: "Project",
      annotations: [{ content: "Yes", style: { fill: "white" } }],
      targetID: "End"
    },
    {
      id: "connector5",
      sourceID: "End",
      annotations: [{ content: "Yes", style: { fill: "white" } }],
      targetID: "transaction_completed"
    },
    {
      id: "connector6",
      sourceID: "transaction_completed",
      targetID: "transaction_entered"
    },
    { id: "connector7", sourceID: "transaction_completed", targetID: "Data" },
    { id: "connector8", sourceID: "transaction_completed", targetID: "node10" },
    {
      id: "connector9",
      sourceID: "node11",
      targetID: "Meeting",
      segments: [{type: "Orthogonal", direction: "Top", length: 120 }]
    },
    {
      id: "connector10",
      sourceID: "End",
      annotations: [{ content: "No", style: { fill: "white" } }],
      targetID: "node11",
      segments: [{type: "Orthogonal", direction: "Right", length: 100 }]
    },
    {
      id: "connector11",
      sourceID: "Project",
      annotations: [{ content: "No", style: { fill: "white" } }],
      targetID: "node11"
    },
    {
      id: "connector12",
      style: { strokeDashArray: "2,2" },
      sourceID: "transaction_entered",
      targetID: "node12"
    }
  ];
  */


let flowshapes = [
    { id: "Task", width: 100, shape: { type: "Flow", shape: "Process" }, style: { fill: '#5BA5F0', strokeColor: 'white' }, ports: [rightPort, leftPort] },
    { id: "Condition", shape: { type: "Flow", shape: "Decision" }, style: { fill: '#ffd11a', strokeColor: 'white' }, ports: [rightPort, leftPort] },
    { id: "Input", shape: { type: "Flow", shape: "Data" }, style: { fill: '#bfbfbf', strokeColor: 'white' }, ports: [rightPort, leftPort] },
    { id: "Wait", shape: { type: "Flow", shape: "Delay" }, style: { fill: '#40bf40', strokeColor: 'white' }, ports: [rightPort, leftPort] }
    /*
    { id: "Subprocess", shape: { type: "Flow", shape: "PreDefinedProcess" }, style: { fill: '#b366ff', strokeColor: 'white' }  },
    { id: "Interaction", shape: { type: "Flow", shape: "Card" }, style: { fill: '#bf8040', strokeColor: 'white' }  },
    */
    /*
    { id: "Terminator", shape: { type: "Flow", shape: "Terminator" } },
    { id: "Process", shape: { type: "Flow", shape: "Process" } },
    { id: "Decision", shape: { type: "Flow", shape: "Decision" } },
    { id: "Document", shape: { type: "Flow", shape: "Document" } },
    { id: "PreDefinedProcess", shape: { type: "Flow", shape: "PreDefinedProcess" } },
    { id: "PaperTap", shape: { type: "Flow", shape: "PaperTap" } },
    { id: "DirectData", shape: { type: "Flow", shape: "DirectData" } },
    { id: "SequentialData", shape: { type: "Flow", shape: "SequentialData" } },
    { id: "Sort", shape: { type: "Flow", shape: "Sort" } },
    { id: "MultiDocument", shape: { type: "Flow", shape: "MultiDocument" } },
    { id: "Collate", shape: { type: "Flow", shape: "Collate" } },
    { id: "SummingJunction", shape: { type: "Flow", shape: "SummingJunction" } },
    { id: "Or", shape: { type: "Flow", shape: "Or" } },
    { id: "InternalStorage", shape: { type: "Flow", shape: "InternalStorage" } },
    { id: "Extract", shape: { type: "Flow", shape: "Extract" } },
    { id: "ManualOperation", shape: { type: "Flow", shape: "ManualOperation" } },
    { id: "Merge", shape: { type: "Flow", shape: "Merge" } },
    { id: "OffPageReference", shape: { type: "Flow", shape: "OffPageReference" } },
    { id: "SequentialAccessStorage", shape: { type: "Flow", shape: "SequentialAccessStorage" } },
    { id: "Annotation", shape: { type: "Flow", shape: "Annotation" } },
    { id: "Annotation2", shape: { type: "Flow", shape: "Annotation2" } },
    { id: "Data", shape: { type: "Flow", shape: "Data" } },
    { id: "Card", shape: { type: "Flow", shape: "Card" } },
    { id: "Delay", shape: { type: "Flow", shape: "Delay" } }
    */
  ]
  
  //Initializes connector symbols for the symbol palette
  let connectorSymbols = [
    {
      id: "Positive",
      type: "Orthogonal",
      sourcePoint: { x: 0, y: 0 },
      targetPoint: { x: 60, y: 60 },
      style: {strokeColor: 'black'},
      sourcePortID: 'rightPort',
      targetPortID: 'leftPort'
        // targetDecorator: { shape: "Arrow" },
        // style: { strokeWidth: 2 }
    },
    {
      id: "Negative",
      type: "Orthogonal",
      sourcePoint: { x: 0, y: 0 },
      targetPoint: { x: 60, y: 60 },
      style: {strokeColor: 'red'},
      sourcePortID: 'rightPort',
      targetPortID: 'leftPort'
      //targetDecorator: { shape: "Arrow" },
      //style: { strokeDashArray: '5.5'}
    },
    /*
    {
      id: "link3",
      type: "Orthogonal",
      sourcePoint: { x: 0, y: 0 },
      targetPoint: { x: 60, y: 60 },
      style: { strokeWidth: 2 },
      targetDecorator: { shape: "None" }
    },
    {
      id: "Link21",
      type: "Straight",
      sourcePoint: { x: 0, y: 0 },
      targetPoint: { x: 60, y: 60 },
      targetDecorator: { shape: "Arrow" },
      style: { strokeWidth: 2 }
    },
    {
      id: "link23",
      type: "Straight",
      sourcePoint: { x: 0, y: 0 },
      targetPoint: { x: 60, y: 60 },
      style: { strokeWidth: 2 },
      targetDecorator: { shape: "None" }
    },
    {
      id: "link33",
      type: "Bezier",
      sourcePoint: { x: 0, y: 0 },
      targetPoint: { x: 60, y: 60 },
      style: { strokeWidth: 2 },
      targetDecorator: { shape: "None" }
    }
    */
  ]

export default {
    nodes: nodes,
    connectors: connectors,
    gridlines: gridlines,
    flowshapes: flowshapes,
    connectorSymbols: connectorSymbols
}