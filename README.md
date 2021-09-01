# munro-service

The munro service aims to provide an API to retrieve a munro data by category and max/min height in meters.

Data is loaded on startup from a CSV file located in the resources folder of the project.

## Installation

Using JDK 11

The file with the data should be place on:
```bash
resources/data/munro_data.csv
```
To start the service:

- From a terminal:
```bash
./gradlew bootRun
```
- From IntelliJ:
```bash
Run MunroServiceApplication class.
```

## Usage

All parameters are optional:
```python
- category:   MUN/TOP  (MUN)
- heightSort: ASC/DESC (DESC)
- nameSort:   ASC/DESC (ASC)
```

Request example:
```bash
curl http://localhost:8080/munros?category=TOP&maxHeightInMeters=2000&minHeightInMeters=900&heightSort=ASC&nameSort=DESC&limit=1
```

Response example:

```python
[
    {
        "Running No": "211",
        "DoBIH Number": "394",
        "Streetmap": "http://www.streetmap.co.uk/newmap.srf?x=266387&y=780691&z=3&sv=266387,780691&st=4&tl=~&bi=~&lu=N&ar=y",
        "Geograph": "http://www.geograph.org.uk/gridref/NN6638780691",
        "Hill-bagging": "http://www.hill-bagging.co.uk/mountaindetails.php?qu=S&rf=394",
        "Name": "Mullach Coire nan Cisteachan [Carn na Caim South Top]",
        "SMC Section": "5",
        "RHB Section": "05B",
        "_Section": "5",
        "Height (m)": 914.6,
        "Height (ft)": 3001.0,
        "Map 1:50": "42",
        "Map 1:25": "OL51W 394W",
        "Grid Ref": "NN663806",
        "GridRefXY": "NN6638780691",
        "xcoord": "266387",
        "ycoord": "780691",
        "1891": "",
        "1921": "",
        "1933": "",
        "1953": "",
        "1969": "",
        "1974": "",
        "1981": "",
        "1984": "",
        "1990": "",
        "1997": "",
        "Post 1997": "TOP",
        "Comments": "New Munro Top September 2015 following surveys"
    }
]
```