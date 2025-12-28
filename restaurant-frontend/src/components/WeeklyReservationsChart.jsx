import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    CartesianGrid,
    ResponsiveContainer, Cell
} from "recharts";

function WeeklyReservationsChart({ data }) {
    const isToday = (day) => {
        const currDay = new Date().getDay();

        switch(day){
            case "Monday": return 1 === currDay;
            case "Tuesday": return 2 === currDay;
            case "Wednesday": return 3 === currDay;
            case "Thursday": return 4 === currDay;
            case "Friday": return 5 === currDay;
            case "Saturday": return 6 === currDay;
            case "Sunday": return 0 === currDay;
        }
    }
    return (
        <ResponsiveContainer width="100%" height={250} >
            <BarChart data={data} margin={{"top" : 5, "bottom" : 5, "left" : 5, "right" : 30}}>
                <CartesianGrid strokeDasharray="1 1"/>
                <XAxis dataKey="day" />
                <YAxis allowDecimals={false} />=
                <Bar dataKey="reservations" elevation={10} radius={[10, 10, 10, 10]} maxBarSize={22}>
                    {data.map((entry, index) => (
                        <Cell
                            key={index}
                            fill={isToday(entry.day) ? "#e83737" : "#d59f4a"}
                        />
                    ))}
                </Bar>
            </BarChart>
        </ResponsiveContainer>
    );
}

export default WeeklyReservationsChart;
